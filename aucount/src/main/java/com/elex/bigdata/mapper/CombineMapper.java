package com.elex.bigdata.mapper;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author: liqiang
 * Date: 14-6-6
 * Time: 下午4:01
 */
public class CombineMapper extends Mapper<Text,NullWritable,Text,IntWritable>{

    private IntWritable count = new IntWritable(1);

    @Override
    protected void map(Text key, NullWritable value, Context context) throws IOException, InterruptedException {
        context.write(key,count);
    }
}
