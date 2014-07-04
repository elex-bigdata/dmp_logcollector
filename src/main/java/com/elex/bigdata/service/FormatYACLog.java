package com.elex.bigdata.service;

import com.elex.bigdata.util.DMUtils;
import com.elex.bigdata.util.YACConstants;
import net.lingala.zip4j.core.ZipFile;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * Author: liqiang
 * Date: 14-7-4
 * Time: 上午9:42
 */
public class FormatYACLog implements Callable<String>{
    public static final Log LOG = LogFactory.getLog(FormatYACLog.class);
    private static Random random = new Random();
    private String zipfilePath;

    public FormatYACLog(String zipfilePath){
        this.zipfilePath = zipfilePath;
    }

    @Override
    public String call() throws Exception {
        FileInputStream fis = null;
        BufferedReader reader = null;

        try{
            LOG.debug("Unzip file " + zipfilePath);
            String filePath = unzipFile(zipfilePath);

            //parseFile
            fis = new FileInputStream(filePath);
            reader = new BufferedReader(new InputStreamReader(fis));

            String firstLine = reader.readLine().replace(YACConstants.YAC_UNICODE,"");
            String line = null;

            while((line =  reader.readLine()) != null){
                if(!YACConstants.YAC_UNICODE.equals(line)){
                    line =  line.replace(YACConstants.YAC_UNICODE, "");
                    List<String> attrs = DMUtils.split(line,YACConstants.LOG_ATTR_SEPRATOR);

                    if(DMUtils.validateURL(attrs.get(1))){
                        //uid  ip nation ts url title 网站语言 metainfo 停留时间
                        StringBuffer sb = new StringBuffer(firstLine);
                        sb.append(YACConstants.LOG_ATTR_SEPRATOR);
                        sb.append(attrs.get(0)).append(getTimeSuffix()).append(YACConstants.LOG_ATTR_SEPRATOR)
                                .append(attrs.get(1)).append(YACConstants.LOG_ATTR_SEPRATOR)
                                .append(attrs.get(2)).append(YACConstants.LOG_ATTR_SEPRATOR)
                                .append(attrs.get(3)).append(YACConstants.LOG_ATTR_SEPRATOR)
                                .append(attrs.get(4)).append(YACConstants.LOG_ATTR_SEPRATOR)
                                .append(attrs.get(5)).append(YACConstants.LOG_ATTR_SEPRATOR);
                        YACConstants.CONTENT_QUEUE.add(sb.toString());
                    }
                }
            }
            LOG.debug("Delete file " + filePath);
            new File(filePath).delete();
        }catch (Exception e){
            e.printStackTrace(); //暂时不理会错误
            return "fail";
        }finally {
            if(reader != null){
                reader.close();
            }
            if(fis != null){
                fis.close();
            }

        }
        return "ok";
    }

    public String unzipFile(String filePath) throws Exception{
        ZipFile zipFile = new ZipFile(filePath);
        String zipFileName = zipFile.getFile().getName();
        String fileName = zipFileName.substring(0,zipFileName.indexOf("."));
        try{
            zipFile.extractAll(YACConstants.unzip_path);
        }catch (Exception e){
            LOG.warn("unzip " + filePath + " " + e.getMessage());
        }

        //new File(filePath).delete(); //删除
        return YACConstants.unzip_path + "/" + fileName + ".dat";
    }

    public String getTimeSuffix(){
        //时间只有10位，手动加上3位随机数
        int randomTime = random.nextInt(999);
        String timeSuffix = String.valueOf(randomTime);
        if(randomTime == 0){
            timeSuffix = "000";
        }if(randomTime<10){
            timeSuffix = "00" + randomTime;
        }else if(randomTime<100){
            timeSuffix = "0" + randomTime;
        }
        return timeSuffix;
    }
}
