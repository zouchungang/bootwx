package com.bootdo.wx.controller;

import com.bootdo.common.redis.shiro.RedisManager;
import com.bootdo.common.utils.R;
import com.wx.httpHandler.HttpResult;
import com.wx.service.BaseService;
import com.wx.service.ServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 微信任务信息
 *
 * @author zcg
 * @email 804188877@qq.com
 * @date 2018-12-27 17:36:15
 */

@Controller
@RequestMapping("/wx/wxoperation")
public class WxOperationController {

    @Autowired
    private static Logger logger = LoggerFactory.getLogger(WxOperationController.class);
    @Autowired
    private RedisManager redisManager;

    @ResponseBody
    @RequestMapping("/getQrCode")
    public R getQrCode(HttpServletRequest request, HttpServletResponse response){

//        String randomid1 = request.getParameter("redisKey");
//        if (true) {
//            redisManager.get(randomid1.getBytes());
//            return new R();
//        }
        R ret=new R();
        String randomid = request.getParameter("randomid");
        String account = request.getParameter("account");
        String softwareId = request.getParameter("softwareId");
//        boolean autoLogin = param.containsKey("autoLogin") ? Boolean.parseBoolean(param.get("autoLogin")) : false;
        boolean autoLogin =  false;
//        String extraData = param.containsKey("extraData") ? param.get("extraData") : "";
        String extraData =  "";
        boolean isNew = false;
        if (randomid == null) {
            isNew = true;
            randomid = UUID.randomUUID().toString();
        }
        if (account == null || randomid.length() == 0) {

            return ret.error(-1,"randomId、account、softwareId 不能为空");
        }

        BaseService baseService = ServiceManager.getInstance().createService(randomid, softwareId, autoLogin, extraData);
        baseService.setSoftwareId(softwareId);
        baseService.setNew(isNew);
        baseService.setAccount(account);
        return ret.put("randomid", randomid);
    }

    /**
     * 获取用户登录二维码
     */
    @ResponseBody
    @PostMapping(value = "/login_qrcode")
    public R getLoginQrcode(HttpServletRequest request,String account,String randomId,String softwareId,
                            Boolean autoLogin,String extraData) {
        logger.info("###### 开始获取户登录二维码 ######");
        long startTime = System.currentTimeMillis();
        String logPrefix = "[获取用登录二维码]";
//        JSONObject po = getJsonParam(request);
//        _log.info("{}请求参数:{}", logPrefix, po);
//        String account = getStringRequired(po,"account");
//        String randomId = getStringRequired(po,"randomId");
//        String softwareId = getStringRequired(po,"softwareId");
//        Boolean autoLogin = getBoolean(po,"autoLogin");
//        String extraData = getString(po,"extraData");
        boolean isNew;
        randomId= UUID.randomUUID().toString();
        BaseService service = ServiceManager.getInstance().getServiceByRandomId(randomId);
        if (service == null) {

            isNew = true;
            BaseService baseService = ServiceManager.getInstance().createService(randomId, softwareId, autoLogin, extraData);
            baseService.setSoftwareId(softwareId);
            baseService.setNew(isNew);
            baseService.setAccount(account);
            return R.ok().put("uuid",randomId);
        }
        long endTime = System.currentTimeMillis();
        logger.info("{}randomId:{}, 耗时：{} ms", logPrefix, randomId, endTime - startTime);

        return R.ok().put("uuid",randomId);
//        return ResponseEntity.ok(DataResponse.buildSuccess(service.getState()));
    }

    /**
     * 获取用户登录状态
     */
    @ResponseBody
    @RequestMapping(value = "/login_status")
    public R getLoginState(HttpServletRequest request,String account,String randomId,String softwareId,
                           Boolean autoLogin,String extraData) {
        logger.info("###### 获取用户登录状态 ######");
        String logPrefix = "[获取用登录状态]";
//        JSONObject po = getJsonParam(request);
//        _log.info("{}请求参数:{}", logPrefix, po);
//        String randomId = getStringRequired(po, "randomId");
        BaseService service = ServiceManager.getInstance().getServiceByRandomId(randomId);
        if (service == null) {
//            return ResponseEntity.ok(BaseResponse.build(RetEnum.RET_COMM_1002));
            return R.error(1002, "null");
        }
        // 这里调用 getQRcode ，拿到二维码， 通过调用这个方法想拿到
      //  BaseService baseService = ServiceManager.getInstance().createService(randomId, softwareId, autoLogin, extraData);
//        return ResponseEntity.ok(DataResponse.buildSuccess(service.getState()));
        return R.ok().put("status",service);
    }

    /**
     * 获取用户登录状态
     */
    @ResponseBody
    @RequestMapping(value = "/getLoginStatus")
    public R getLoginStatus(HttpServletRequest request,String randomId) {
        logger.info("###### 获取用户登录状态 ######");
        String logPrefix = "[获取用登录状态]";

        BaseService service = ServiceManager.getInstance().getServiceByRandomId(randomId);
        if (service == null) {
            return R.error(-1,"未找到相应数据");
        }
        if (true) {
            return R.ok().put("state",service);
        }
//        } else if (uri.equals("getlogin")) {
//            if (service.getState().code == 2) {
//                service.login();
//                service.getState().code = 6;
//                service.getState().msg = "登录完成.";
//            } else {
//                service.getState().code = 6;
//                service.getState().msg = "已经登录,请勿重复调用接口.";
//            }
//            return service.getState();
//        } else if (uri.equals("getlogout")) {
//            logger.info("randomid:" + randomid + " ; 已退出ipad登陆");
//            service.setIsDead(true);
//            return new HttpResult(0, "操作成功");
//        } else {
//            return service.handleHttpRequest(uri, param);
//        }

        if (service == null) {
//            return ResponseEntity.ok(BaseResponse.build(RetEnum.RET_COMM_1002));
            return R.error(1002, "null");
        }
        return R.ok().put("status",service);
    }

    @ResponseBody
    @RequestMapping("/setQrCode")
    public R setQrCode(HttpServletRequest request, HttpServletResponse response){
        String randomid = request.getParameter("redisKey");
        String randomids = request.getParameter("redisValue");

        redisManager.set(randomid.getBytes(), randomids.getBytes());
        return new R();

    }

}
