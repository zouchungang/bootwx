package com.wx.service;

import com.google.common.base.Strings;
import com.wx.httpHandler.MyHttpHandler;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServiceManager {
    private static Logger logger = Logger.getLogger(MyHttpHandler.class);
    private static final ConcurrentHashMap<String, BaseService> serviceMap = new ConcurrentHashMap<String, BaseService>(10000, 0.90f, Runtime.getRuntime().availableProcessors() * 2);
    private static final ScheduledExecutorService serviceMonitor = Executors.newScheduledThreadPool(5);
    private static final ServiceManager instance = new ServiceManager();

    static {
        serviceMonitor.scheduleAtFixedRate(() -> {
            try {
                for (Iterator<Map.Entry<String, BaseService>> it = serviceMap.entrySet().iterator(); it.hasNext(); ) {
                    Map.Entry<String, BaseService> item = it.next();
                    BaseService service = item.getValue();
                    if (service.isDead()) {
                        service.exit();
                        it.remove();
                    }
                }
            } catch (Exception e) {
                logger.info(e);
            }
        }, 2, 2, TimeUnit.SECONDS);
    }

    public static ServiceManager getInstance() {
        return instance;
    }
//这个就是逻辑idsoftwareId，你比如说，这个号，只做阅读，那么我定义softwareId=888，我还有个号，要刷朋友圈，那么我可以传入softwareId=77就是不同的逻辑，而不会乱
    //这个是为了微服务而定义的
    public BaseService createService(String randomid, String softwareId, boolean autoLogin, String extraData) {
        BaseService service = serviceMap.get(randomid);
        if (service == null) {//根据softwareId来进入或者创建逻辑类
            if (!Strings.isNullOrEmpty(softwareId) && softwareId.equals(ServiceSoftwareId.SOFTWARE_Demo.softwareId)) {
                service = new Service_Demo(randomid);//这就是最初，创建属于这个微信号的时候的地方
            }else if (!Strings.isNullOrEmpty(softwareId) && softwareId.equals(ServiceSoftwareId.SOFTWARE_Demo.softwareId)) {
                service = new Service_Demo(randomid);//这就是最初，创建属于这个微信号的时候的地方
            } else {
                service = new Service_Demo(randomid);
            }

            BaseService finalService = service;//没有找到就创建一个，然后在下面，吧randomid和server进行绑定
            service.connectToWx(data -> {//这里就是创建之后调用getqrcode
                finalService.getQRcode();
            });
            serviceMap.put(randomid, service);
        } else if (service.getState().getCode() <= 0) {
            service.getQRcode();
        }
        service.setAutoLogin(autoLogin);
        service.setExtraData(extraData);
        return service;
    }

    public BaseService createServiceForReLogin(String randomid, String softwareId) {
        BaseService service = serviceMap.get(randomid);
        if (service == null && softwareId != null) {
            if (softwareId.equals(ServiceSoftwareId.SOFTWARE_Demo.softwareId)) {
                service = new Service_Demo(randomid);
            } else {
                service = new Service_Demo(randomid);
            }
            serviceMap.put(randomid, service);//绑定，返回server
        }
        return service;
    }

    public BaseService getServiceByRandomId(String randomId) {
        return serviceMap.get(randomId);
    }
}
