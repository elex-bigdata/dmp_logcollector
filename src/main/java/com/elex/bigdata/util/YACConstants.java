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

    public static final String unzip_path = "/data0/log/yac/dat";
    public static final String upload_dir_path = "/data0/log/yac/zip";

    public static final String log_dir_path = "/data0/log/yac/";


    public static final String url_filter_keywords = "callback=|\\.xml|=http|=www|video|img|static|amasvc|google|youtube|google-analytics|googlebanner|ad\\.|ads|ad2|\\.ad|192\\.168\\.|\\.ytimg\\.com|ajax|api|image|favicon|\\.doubleclick|s\\.youtube\\.com|&async=|=async&|livetr\\.gostream\\.nl|script|---|___|/cdn|cdn\\.|\\.virgul\\.com|adserver|yandex\\.|graph\\.facebook\\.com|my\\.mail|redtube\\.com";
    public static final String url_filter_suffix = "gif|GIF|jpg|JPG|png|PNG|ico|ICO|css|flv|CSS|sit|SIT|eps|EPS|wmf|WMF|zip|ZIP|ppt|PPT|mpg|MPG|xls|XLS|gz|GZ|rpm|RPM|tgz|TGZ|mov|MOV|exe|EXE|jpeg|JPEG|bmp|BMP|js|JS|jxr|f3d|woff|svg|AMF|amf|ttf|swf|txt|mp3";

    public static final String amazon_view_product_reg = "http://www.amazon.com/[a-zA-Z0-9\\-]+/dp/.*";
    public static final String ebay_view_product_reg = "http://www.ebay.com/itm/[a-zA-Z0-9\\-]+/.*";

    public static String URL_FILTER_REG = ".*(("+url_filter_keywords+")|(/"+url_filter_suffix+")|(\\.("+url_filter_suffix+"))).*";
//    public static String URL_FILTER_REG = ".*\\.("+url_filter_suffix+")(\\?.+|$)";

    public static Pattern URL_FILTER_PATTERN = Pattern.compile(URL_FILTER_REG);
    public static Pattern AMAZON_VIEWPRODUCT_PATTERN = Pattern.compile(amazon_view_product_reg);
    public static Pattern EBAY_VIEWPRODUCT_PATTERN = Pattern.compile(ebay_view_product_reg);

    public static String YAC_UNICODE = "\u0000";
    public static final String LOG_ATTR_SEPRATOR = "\t";

    public static String url_doubt_keywords = "ad\\.|ads\\.|ad2\\.|\\.ad|image";
    public static Pattern URL_DOUBT_PATTERN = Pattern.compile(".*("+url_doubt_keywords+").*");
}