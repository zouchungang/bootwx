package com.bootdo.common.service.impl;

import com.bootdo.baseinfo.dao.WechatDao;
import com.bootdo.baseinfo.domain.WechatDO;
import com.bootdo.common.aspect.LogAspect;
import com.bootdo.common.service.TaskJobService;
import com.bootdo.system.dao.ConfigDao;
import com.bootdo.system.domain.ConfigDO;
import com.bootdo.wx.dao.TaskinfoDao;
import com.bootdo.wx.domain.TaskinfoDO;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TaskJobServiceImpl implements TaskJobService {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    TaskinfoDao taskinfoDao;
    @Autowired
    private ConfigDao configDao;
    @Autowired
    private WechatDao wechatDao;

    @Transactional
    @Override
    public void run() {
        try {
            // 获取任务信息
            List<TaskinfoDO> taskinfoDOS = taskinfoDao.waitTaskList();
            if (taskinfoDOS.size() > 0) {
                for (int i = 0; i < taskinfoDOS.size(); i++) {
                    TaskinfoDO taskinfo = taskinfoDOS.get(i);
                    // 提取任务微信号，判断是否有足够的微信号, 冷却时间、当日上限数量、状态及绑定任务
                    Map<String, Object> configMap = Maps.newHashMap();
                    configMap.put("key", "cdtime");
                    List<ConfigDO> configDos = configDao.list(configMap);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date now = new Date();
                    Calendar nowTime = Calendar.getInstance();
                    nowTime.add(Calendar.MINUTE, Integer.parseInt(configDos.get(0).getValue()));

                    configMap.put("key", "todaytaskquantity");
                    configDos = configDao.list(configMap);
                    Map<String, Object> wxMap = Maps.newHashMap();
                    wxMap.put("lastdate", sdf.format(nowTime.getTime()));
                    wxMap.put("todaytaskquantity", Integer.parseInt(configDos.get(0).getValue()));
                    wxMap.put("limit", taskinfo.getNum());
                    List<WechatDO> wechatListdb = wechatDao.list(wxMap);

                    if (wechatListdb.size() == taskinfo.getNum()) {// 有足够的微信号，开始将微信号绑定到任务上
                        for (int j = 0; j <= wechatListdb.size(); j++) {
                            WechatDO wechatDO = wechatListdb.get(j);
                            wechatDO.setTaskid(taskinfo.getId());
                            wechatDao.update(wechatDO);
                        }
                        // 绑定完任务，启个线程开始做任务 --------------------功能待开发-------------------------------


                        //----------------------------------------------------任务结束---------------------------------
                    } else {
                        break;
                    }

                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("com.bootdo.common.task.TaskJob->exception!message:{},cause:{},detail{}", e.getMessage(), e.getCause(), e.toString());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }
}
