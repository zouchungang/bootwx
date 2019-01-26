package com.bootdo.api;

import com.google.common.collect.Maps;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

public class test2 {


    @Test
    public static void main(String[] args) {
        Wxxy qdPos=Wxxy.INSTANCE;
        Pointer pointer = new Memory(50000);
        String uuid = UUID.randomUUID().toString();
        PointerByReference ret9 = new PointerByReference();
        PointerByReference ret10 = new PointerByReference();
        String json = null;
        int x=qdPos.WXInitialize(pointer,"a","<softtype><k3>9.0.2</k3><k9>iPad</k9><k10>2</k10><k19>58BF17B5-2D8E-4BFB-A97E-38F1226F13F8</k19><k20>"+uuid+"</k20><k21>neihe_5GHz</k21><k22>(null)</k22><k24>34-97-F6-90-66-89</k24><k33>\\345\\276\\256\\344\\277\\241</k33><k47>1</k47><k50>1</k50><k51>com.tencent.xin</k51><k54>iPad4,4</k54></softtype>",uuid);
        System.out.println("初始化设备指针→"+x);

        System.out.println("\n获取url访问token："+qdPos.WXGetRequestToken(x,"郑州住房公积金管理中心","https://mp.weixin.qq.com/s/ORGbl1e_kiJK4enMVJMkRA",ret9));
        String tokenLicecseUrl = ret9.getValue().getString(0, "UTF-8");
        Map<String,Object> urlMap= Maps.newHashMap();
        json = ret9.getValue().getString(0, "UTF-8");
        urlMap = com.bootdo.common.utils.JSONUtils.jsonToMap(json);
        String xWechatKey = "";
        String uin = "";
        String fullUrl = "";

        if (urlMap != null) {
            String info=urlMap.get("info").toString();
            Map<String,Object> infoMap= Maps.newHashMap();
            infoMap=com.bootdo.common.utils.JSONUtils.jsonToMap(info);
            xWechatKey=infoMap.get("X-WECHAT-KEY").toString();
            fullUrl=urlMap.get("full_url").toString();
            uin=infoMap.get("X-WECHAT-UIN").toString();
        }
        System.out.println("获取url访问token Info：{"+tokenLicecseUrl+"},key:{"+xWechatKey+"},uin:{"+uin+"}");



        System.out.println("\n访问url："+qdPos.WXRequestUrl(x,fullUrl,xWechatKey,uin,ret10));
        String tokenLicecseVisit = ret10.getValue().getString(0, "UTF-8");
        System.out.println("访问url-info："+tokenLicecseVisit);
    }
}
