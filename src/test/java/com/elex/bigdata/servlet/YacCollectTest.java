package com.elex.bigdata.servlet;

import java.io.IOException;

/**
 * Author: liqiang
 * Date: 14-6-9
 * Time: 下午2:39
 */
public class YacCollectTest {

    public static void main(String[] args) throws IOException {

        FileUpload fu = new FileUpload();

        String file = "d:/upload.txt";
        String xx = fu.send("http://localhost:8080/dmp/upload?ts=123123",file);
        System.out.println(xx);
    }
}
