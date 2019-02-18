package com.wx.frdemo;

import com.wx.frdemo.KeyValues;
import com.wx.frdemo.ParamNames;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Main {

    private String merchantCode = "12900002";//商户号
    private String merchantKey = "6h3b399bfad111e4bcb600259079b2ad";//商户KEY
    private String bankAccount = "哈德司";//银行户名
    private String bankCardNo = "6212261908003454470";//银行账号
    private String bankCode = "ICBC";//银行编码
    private String amount = "0.01";//金额
    private String getUrl = "http://pay.41.cn/remit";

    public static void main(String[] args) {
        Main m = new Main();
        m.remit();
    }

    private void remit()
    {
        Map<String, String> params = new HashMap<>();
        params.put(ParamNames.INPUT_CHARSET, "UTF-8");
        params.put(ParamNames.MERCHANT_CODE, merchantCode);
        params.put(ParamNames.BANK_ACCOUNT, bankAccount);
        params.put(ParamNames.BANK_CARD_NO, bankCardNo);
        params.put(ParamNames.BANK_CODE, bankCode);
        params.put(ParamNames.AMOUNT, amount);
        params.put(ParamNames.MERCHANT_ORDER, String.valueOf(new java.util.Random().nextInt(100000000)));
        String sign = KeyValues.create()
                .add(ParamNames.INPUT_CHARSET, params.get(ParamNames.INPUT_CHARSET))
                .add(ParamNames.MERCHANT_CODE, params.get(ParamNames.MERCHANT_CODE))
                .add(ParamNames.BANK_ACCOUNT, params.get(ParamNames.BANK_ACCOUNT))
                .add(ParamNames.BANK_CARD_NO, params.get(ParamNames.BANK_CARD_NO))
                .add(ParamNames.BANK_CODE, params.get(ParamNames.BANK_CODE))
                .add(ParamNames.AMOUNT, params.get(ParamNames.AMOUNT))
                .add(ParamNames.MERCHANT_ORDER, params.get(ParamNames.MERCHANT_ORDER))
                .sign(merchantKey);
        params.put(ParamNames.SIGN, sign);
        StringBuilder sb = new StringBuilder();
        sb.append(getUrl)
                .append("?").append(ParamNames.INPUT_CHARSET).append("={").append(ParamNames.INPUT_CHARSET).append("}")
                .append("&").append(ParamNames.MERCHANT_CODE).append("={").append(ParamNames.MERCHANT_CODE).append("}")
                .append("&").append(ParamNames.BANK_ACCOUNT).append("={").append(ParamNames.BANK_ACCOUNT).append("}")
                .append("&").append(ParamNames.BANK_CARD_NO).append("={").append(ParamNames.BANK_CARD_NO).append("}")
                .append("&").append(ParamNames.BANK_CODE).append("={").append(ParamNames.BANK_CODE).append("}")
                .append("&").append(ParamNames.AMOUNT).append("={").append(ParamNames.AMOUNT).append("}")
                .append("&").append(ParamNames.MERCHANT_ORDER).append("={").append(ParamNames.MERCHANT_ORDER).append("}")
                .append("&").append(ParamNames.SIGN).append("={").append(ParamNames.SIGN).append("}");
        String url = sb.toString();
        String result = sendGet(url, params);
        System.out.println("request result is " + ("success".equals(result) ? "结算成功" : result));
    }

    public static String sendGet(String url, Map<String, String> params) {
        String result = "";
        BufferedReader in = null;
        try {
            Set<Map.Entry<String, String>> sets = params.entrySet();
            for (Map.Entry<String, String> entry : sets) {
                String k = entry.getKey();
                String v = entry.getValue();
                url = url.replace("{" + k + "}", v);
            }
            System.out.println("request url is " + url);
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
}
