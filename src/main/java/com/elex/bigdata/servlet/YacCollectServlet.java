package com.elex.bigdata.servlet;

import com.elex.bigdata.util.DMUtils;
import com.elex.bigdata.util.YACConstants;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Author: liqiang
 * Date: 14-6-6
 * Time: 下午1:59
 */
public class YacCollectServlet extends HttpServlet {

    public static final Log LOG = LogFactory.getLog(YacCollectServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String decode = req.getParameter("ts");
        if(decode == null || decode.length() < 4){
            resp.getWriter().write("{'status':'fail','msg':'param ts is invalid'}");
            return;
        }

        //TODO: 如果今天接收的数据量够多了，停止接收

        try {
            req.setCharacterEncoding("utf-8");  //设置编码

            /*SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String day = sdf.format(new Date());*/
            String path = YACConstants.upload_dir_path;

            String ip = req.getHeader("X-Forwarded-For") != null ? req.getHeader("X-Forwarded-For") : req.getRemoteAddr();
            //TODO；存IP
            File file = new File(path);
            if( !file.isDirectory()){
                file.mkdirs();
            }

            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(new File(path));
            factory.setSizeThreshold(1024*1024) ;

            ServletFileUpload upload = new ServletFileUpload(factory);

            //可以上传多个文件
            List<FileItem> list = (List<FileItem>)upload.parseRequest(req);
            for(FileItem item : list){

                if(!item.isFormField()){

                    String value = item.getName() ;
                    String filename = value.substring(value.lastIndexOf("\\")+1);

                    String sed = decode.substring(decode.length() - 4);
                    LOG.debug("filename : " + filename + "， IP : " + ip + "， Seed : " + sed + ", size :" + item.getSize());

                    OutputStream out = new FileOutputStream(new File(path,filename));
                    InputStream in = item.getInputStream() ;

                    int length = 0 ;
                    byte [] buf = new byte[1024] ;

                    byte[] seed =sed.getBytes();
                    int currentPosition = 0;
                    while( (length = in.read(buf) ) != -1){
                        //解密
                        DMUtils.decode(buf,seed,currentPosition);
                        currentPosition += length;

                        out.write(buf, 0, length);
                    }

                    in.close();
                    out.close();
                    YACConstants.FILENAME_QUEUE.add(path + "/" + filename);
                }
            }
            resp.getWriter().write("{'status':'success','msg':''}");
        }catch (Exception e) {
            LOG.error(e.getMessage());
            resp.getWriter().write("{'status':'fail','msg':'"+e.getMessage()+"'}");
        }

    }
}
