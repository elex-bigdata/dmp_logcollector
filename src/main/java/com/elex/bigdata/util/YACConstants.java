package com.elex.bigdata.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Pattern;

/**
 * Author: liqiang
 * Date: 14-7-4
 * Time: 上午9:44
 */
public class YACConstants {

    public static BlockingQueue<String> FILENAME_QUEUE = new LinkedBlockingQueue<String>();

    public static BlockingQueue<String> CONTENT_QUEUE = new LinkedBlockingQueue<String>();

    public static final String unzip_path = "/data/log/yac/dat";
    public static final String upload_dir_path = "/data/log/yac/zip";

    public static final String log_dir_path = "/data/log/yac/";

    public static final String url_filter_suffix = "gif|GIF|jpg|JPG|png|PNG|ico|ICO|css|CSS|sit|SIT|eps|EPS|wmf|WMF|zip|ZIP|ppt|PPT|mpg|MPG|xls|XLS|gz|GZ|rpm|RPM|tgz|TGZ|mov|MOV|exe|EXE|jpeg|JPEG|bmp|BMP|js|JS|jxr|swf|SWF";
    public static String URL_FILTER_REG = ".*\\.("+url_filter_suffix+")(\\?.*|$)";

    public static Pattern URL_FILTER_PATTERN = Pattern.compile(URL_FILTER_REG);

    public static String YAC_UNICODE = "\u0000";
    public static final String LOG_ATTR_SEPRATOR = "\t";
}