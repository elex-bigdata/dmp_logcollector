package com.elex.bigdata.main;

import com.elex.bigdata.job.QuartorJob;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Author: liqiang
 * Date: 14-6-7
 * Time: 上午11:31
 */
public class Main {

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {

        if(args.length != 1){
            System.err.println("Usage : filename");
            System.exit(-1);
        }

        String fpath = args[0];
        File pfile = new File(fpath);
        FileReader fr = new FileReader(pfile);
        BufferedReader reader = new BufferedReader(fr);
        String p = null;
        List<String> pjs = new ArrayList<String>();
        while((p = reader.readLine() )!=null){
            pjs.add(p.trim());
        }

        ExecutorService service = new ThreadPoolExecutor(16,16,60, TimeUnit.MILLISECONDS,new LinkedBlockingDeque<Runnable>());
        List<Future<Integer>> tasks = new ArrayList<Future<Integer>>();
        for(int i =0;i<16; i++){
            String nodename = "node" + i;
            tasks.add(service.submit(new QuartorJob(nodename, pjs)));
        }

        for(Future f : tasks){
            try {
                f.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        service.shutdown();

    }
}
