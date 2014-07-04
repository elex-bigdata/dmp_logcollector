package com.elex.bigdata.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: liqiang
 * Date: 14-6-13
 * Time: 上午9:19
 */
public class DMUtils {

    public static void decode(byte[] b,byte[] seed,int currentPosition) throws IOException {
        int len = b.length;
        int step = 4;

        for(int i=0;i<len - step + 1  ; i += step){
            for(int j=0;j<step;j++){
                b[i + j] = (byte)(b[i + j] ^ seed[j]);
            }

            byte[] tseed = intToByteArray((currentPosition + i)/step);
            for(int k=0;k<4;k++){
                seed[k] ^= tseed[k];
            }
        }
    }

    public static byte[] intToByteArray(int a){
        return new byte[] { (byte) ((a >> 24) & 0xFF), (byte) ((a >> 16) & 0xFF), (byte) ((a >> 8) & 0xFF), (byte) (a & 0xFF) };
    }

    public static boolean validateURL(String url) {
        return url.length() < 800 && !YACConstants.URL_FILTER_PATTERN.matcher(url).matches() ;
    }

    public static List<String> split(String line, String sep) {
        List<String> attrs = new ArrayList<String>();
        int pos = 0, end;
        while ((end = line.indexOf(sep, pos)) >= 0) {
            attrs.add(line.substring(pos, end));
            pos = end + sep.length();
        }
        attrs.add(line.substring(pos)); //最后一个
        return attrs;
    }
}
