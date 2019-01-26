package com.wx.httpHandler;

import com.google.common.base.Strings;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.wx.bean.RedisBean;
import com.wx.service.BaseService;
import com.wx.service.ServiceManager;
import com.wx.tools.ConfigService;
import com.wx.tools.Constant;
import com.wx.tools.RedisUtils;
import com.wx.tools.StringUtil;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by hupt on 2017/6/29.
 */
public class MyHttpHandler implements HttpHandler {
    private static Logger logger = Logger.getLogger(MyHttpHandler.class);

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String query = httpExchange.getRequestURI().getQuery();
        String uri = httpExchange.getRequestURI().getPath().split("/")[2];
        HashMap<String, String> parameters = new HashMap<String, String>();
        if (query != null && !query.equals("")) {
            String[] paramArr = query.split("&");
            for (int i = 0; i < paramArr.length; i++) {
                String[] keyValue = paramArr[i].split("=");
                if (keyValue.length == 2) {
                    parameters.put(paramArr[i].split("=")[0], paramArr[i].split("=")[1]);
                }
            }
        }
        HttpResult result = handleMsg(uri, parameters);
        String resultStr = result == null ? "" : result.toString();
        httpExchange.getResponseHeaders().set("Content-Type", "text/html; charset=utf-8");
        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, resultStr.getBytes("utf-8").length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(resultStr.getBytes("utf-8"));
        os.flush();
        os.close();
        httpExchange.close();
    }

    public HttpResult handleMsg(String uri, HashMap<String, String> param) {
        try {
            String randomid = param.get("randomid");
            String account = param.get("account");
            String softwareId = param.get("softwareId");
            boolean autoLogin = param.containsKey("autoLogin") ? Boolean.parseBoolean(param.get("autoLogin")) : false;
            String extraData = param.containsKey("extraData") ? param.get("extraData") : "";
            boolean isNew = false;
            if (randomid == null) {
                isNew = true;
                randomid = UUID.randomUUID().toString();
            }
            if (account == null || randomid.length() == 0) {
                return new HttpResult(-1, "randomId、account、softwareId 不能为空");
            }

            if (uri.equals("getqrcode")) {
                BaseService baseService = ServiceManager.getInstance().createService(randomid, softwareId, autoLogin, extraData);
                baseService.setSoftwareId(softwareId);
                baseService.setNew(isNew);
                baseService.setAccount(account);
                return new HttpResult(0, randomid);
            } else if (uri.equals("getloginagain")) {
                BaseService baseService = ServiceManager.getInstance().createServiceForReLogin(randomid, softwareId);
                baseService.setSoftwareId(softwareId);
                baseService.setAccount(account);
                return baseService.loginAgain(param);
            } else {
                BaseService service = ServiceManager.getInstance().getServiceByRandomId(randomid);
                if (service == null) {
                    return new HttpResult(-1, "未找到相应数据");
                }
                if (uri.equals("getstate")) {
                    return service.getState();
                } else if (uri.equals("getlogin")) {
                    if (service.getState().code == 2) {
                        service.login();
                        service.getState().code = 6;
                        service.getState().msg = "登录完成.";
                    } else {
                        service.getState().code = 6;
                        service.getState().msg = "已经登录,请勿重复调用接口.";
                    }
                    return service.getState();
                } else if (uri.equals("getlogout")) {
                    logger.info("randomid:" + randomid + " ; 已退出ipad登陆");
                    service.setIsDead(true);
                    return new HttpResult(0, "操作成功");
                } else {
                    return service.handleHttpRequest(uri, param);
                }
            }
        } catch (Exception e) {
            logger.info(e);
            return new HttpResult(-2, "未找到相应数据");
        }
    }
}
