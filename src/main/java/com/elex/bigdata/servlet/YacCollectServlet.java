package com.elex.bigdata.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Author: liqiang
 * Date: 14-6-6
 * Time: 下午1:59
 */
public class YacCollectServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取输入流，是HTTP协议中的实体内容
        ServletInputStream in= req.getInputStream();
        //缓冲区
        byte buffer[]=new byte[1024];

        FileOutputStream out=new FileOutputStream("d:\\test.log");

        int len = 0;
        //把流里的信息循环读入到file.log文件中
        while( (len = in.read(buffer, 0, 1024)) != -1 ){
            out.write(buffer, 0, len);
        }
        out.close();
        in.close();

    }
}
