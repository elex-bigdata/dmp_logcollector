package com.elex.bigdata.servlet;

import com.elex.bigdata.util.DMUtils;
import com.elex.bigdata.util.YACConstants;
import net.lingala.zip4j.core.ZipFile;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Author: liqiang
 * Date: 14-7-4
 * Time: 上午10:18
 */
public class CleanTest {
    public static String YAC_UNICODE = "\u0000";
    private static final String LOG_ATTR_SEPRATOR = "\t";

    public static void main(String[] args) throws Exception{
        String zipfilePath = "d:/3B670647.zip";
        //unzip
        String filePath = unzipFile(zipfilePath);

        //parseFile
        FileInputStream fis = null;
        BufferedReader reader = null;
        fis = new FileInputStream(filePath);
        reader = new BufferedReader(new InputStreamReader(fis));

        String firstLine = reader.readLine().replace(YAC_UNICODE,"");

        String line = null;

        while((line =  reader.readLine()) != null){
            if(!YAC_UNICODE.equals(line)){
                line =  line.replace(YAC_UNICODE, "");

                String url = line.split(LOG_ATTR_SEPRATOR)[1];

                if(DMUtils.validateURL(url)){
                    System.out.println(firstLine + "\t" + line);
                }
            }
        }
        //remove zip

    }

    public static String unzipFile(String filePath) throws Exception{
        ZipFile zipFile = new ZipFile(filePath);
        String zipFileName = zipFile.getFile().getName();
        String fileName = zipFileName.substring(0,zipFileName.indexOf("."));
        zipFile.extractAll(YACConstants.unzip_path);
        return YACConstants.unzip_path + "/" + fileName + ".dat";
    }
}
