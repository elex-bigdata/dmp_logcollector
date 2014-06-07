package com.elex.bigdata.main;

import com.elex.bigdata.job.QuartorJob;

import java.io.IOException;

/**
 * Author: liqiang
 * Date: 14-6-7
 * Time: 上午11:31
 */
public class Main {

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {

        if(args.length != 2){
            System.err.println("Usage : node project");
            System.exit(-1);
        }

        String node = args[0];
        String project = args[1];
        System.out.println("node : "  + node + ", project : " + project);
        QuartorJob qj = new QuartorJob(node,project);
        qj.run();

    }
}
