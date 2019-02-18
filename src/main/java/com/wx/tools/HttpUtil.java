package com.wx.tools;


import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;
public class HttpUtil {
    private static Logger logger = Logger.getLogger(HttpUtil.class);
    private static OkHttpClient client;
    private static HttpUtil util = new HttpUtil();
    private HttpUtil() {
        client = new OkHttpClient.Builder().followRedirects(false).readTimeout(15, TimeUnit.SECONDS).writeTimeout(15, TimeUnit.SECONDS).connectTimeout(1, TimeUnit.SECONDS).build();
    }
    public static byte[] get(String url) throws IOException {
//        logger.info(url);
        Request.Builder req = new Request.Builder().url(url).
                header("Accept", "*/*").
                header("Content-Type", "application/octet-stream").
//                header("Connection", "keep-alive").
        header("User-Agent", "MicroMessenger Client").get();
        Call call2 = client.newCall(req.build());
        Response arg1 = call2.execute();
        byte[] bytes = arg1.body().bytes();
        return bytes;
    }
    public byte[] get(String url, long timeStamp, String sign) throws IOException {
//        logger.info(url);
        Request.Builder req = new Request.Builder().url(url).
                header("Accept", "*/*").
                header("Content-Type", "application/octet-stream").
//                header("Connection", "keep-alive").
        header("assistant-sign", sign).
                        header("assistant-expireTime", String.valueOf(timeStamp)).
                        header("User-Agent", "MicroMessenger Client").get();
        Call call2 = client.newCall(req.build());
        Response arg1 = call2.execute();
        byte[] bytes = arg1.body().bytes();
        return bytes;
    }
    public static HttpUtil getUtil() {
        return util;
    }
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("content-type", "");
            conn.setRequestProperty("user-agent", "MicroMessenger Client");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            logger.info(e);
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
