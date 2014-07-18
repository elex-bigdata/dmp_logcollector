package com.elex.bigdata.service;

import com.elex.bigdata.util.YACConstants;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Author: liqiang
 * Date: 14-7-4
 * Time: 上午11:48
 */
public class CheckYacZipFile implements Runnable {

    private ExecutorService service = new ThreadPoolExecutor(10,300,60, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());

    @Override
    public void run() {
        String zipFilePath = null;
        while(true){
            zipFilePath = YACConstants.FILENAME_QUEUE.poll();
            if(zipFilePath != null){
                service.submit(new FormatYACLog(zipFilePath));
            }else{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
