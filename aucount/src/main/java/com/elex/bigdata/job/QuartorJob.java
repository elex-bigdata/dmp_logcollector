package com.elex.bigdata.job;

import com.elex.bigdata.mapper.QuartorMapper;
import com.elex.bigdata.reducer.QuartorCombiner;
import com.elex.bigdata.reducer.QuartorReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.KeyOnlyFilter;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Author: liqiang
 * Date: 14-6-7
 * Time: 上午10:35
 */
public class QuartorJob {

    private byte[] table;
    private String project;
    private String node;
    private Path outputpath;

    public QuartorJob(String node, String project){
        this.table = Bytes.toBytes("deu_" + project);
        this.project = project;
        this.node = node;
        this.outputpath = new Path("/user/hadoop/quartorcount/" + node + "/" + project);
    }

    public int run() throws IOException, ClassNotFoundException, InterruptedException {

        Scan scan = new Scan();
        scan.setStopRow(Bytes.toBytes("20110101visit"));
        scan.setStopRow(Bytes.toBytes("20140101visit"));
        scan.setFilter(new KeyOnlyFilter());

        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", node);
        conf.set("hbase.zookeeper.property.clientPort", "3181");
        conf.set("mapred.map.child.java.opts","-Xmx256m") ;
        conf.set("mapred.reduce.child.java.opts","-Xmx256m") ;

        Job job = new Job(conf,node + "_" + project);
        job.setJarByClass(QuartorJob.class);
        TableMapReduceUtil.initTableMapperJob(table,scan, QuartorMapper.class,Text.class, IntWritable.class,job);
        job.setCombinerClass(QuartorCombiner.class);
        job.setReducerClass(QuartorReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        FileOutputFormat.setOutputPath(job,this.outputpath);


        job.waitForCompletion(true);

        if (job.isSuccessful()) {
            return 0;
        } else {
            return -1;
        }
    }

}
