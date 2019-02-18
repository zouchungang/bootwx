package com.wx.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
public class StringUtil {
    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0) {
            return true;
        } else {
            return str.length() == 0;
        }
    }
    public static String subStringWith(String target, String start, String end) {
        try {
            String res = target.substring(target.indexOf(start) + start.length(),
                    target.indexOf(end, target.indexOf(start) + start.length()));
            return res;
        } catch (Exception e) {
        }
        return "";
    }
    public static String decodeURIComponent(String str) {
        if (str == null)
            return "";
        try {
            return URLDecoder.decode(str.replace("\"", ""), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
    public static String encodeURIComponent(String str) {
        if (str == null)
            return "";
        try {
            return URLEncoder.encode(str.replace("\"", ""), "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
    public static String encodeURIComponent2(String str) {
        if (str == null)
            return "";
        try {
            return URLEncoder.encode(str, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
