package com.elex.bigdata.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

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

    public static String prefixPath = "/data/upload/";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");  //设置编码

        //获得磁盘文件条目工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //获取文件需要上传到的路径

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String day = sdf.format(new Date());

        String path = prefixPath + day;
        String decode = req.getParameter("ts");

        System.out.println("Decode : " + decode);

        File file = new File(path);
        if( !file.exists() || !file.isDirectory()){
            file.mkdir();
        }
        System.out.println("Path : " + path);


        //如果没以下两行设置的话，上传大的 文件 会占用 很多内存，
        //设置暂时存放的 存储室 , 这个存储室，可以和 最终存储文件 的目录不同
        /**
         * 原理 它是先存到 暂时存储室，然后在真正写到 对应目录的硬盘上，
         * 按理来说 当上传一个文件时，其实是上传了两份，第一个是以 .tem 格式的
         * 然后再将其真正写到 对应目录的硬盘上
         */
        factory.setRepository(new File(path));
        //设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
        factory.setSizeThreshold(1024*1024) ;

        //高水平的API文件上传处理
        ServletFileUpload upload = new ServletFileUpload(factory);

        try {
            //可以上传多个文件
            List<FileItem> list = (List<FileItem>)upload.parseRequest(req);

            for(FileItem item : list){
                //获取表单的属性名字
                String name = item.getFieldName();

                //如果获取的 表单信息是普通的 文本 信息
                if(item.isFormField()){
                    //获取用户具体输入的字符串 ，名字起得挺好，因为表单提交过来的是 字符串类型的
                    String value = item.getString() ;

                    req.setAttribute(name, value);
                }
                //对传入的非 简单的字符串进行处理 ，比如说二进制的 图片，电影这些
                else
                {
                    /**
                     * 以下三步，主要获取 上传文件的名字
                     */
                    //获取路径名
                    String value = item.getName() ;
                    //索引到最后一个反斜杠
                    int start = value.lastIndexOf("\\");
                    //截取 上传文件的 字符串名字，加1是 去掉反斜杠，
                    String filename = value.substring(start+1);


                    //真正写到磁盘上
                    //它抛出的异常 用exception 捕捉

                    item.write( new File(path,filename) );//第三方提供的


                }
            }



        } catch (FileUploadException e) {
            e.printStackTrace();
        }catch (Exception e) {

            e.printStackTrace();
        }

        resp.getWriter().write("ok");

    }
}
