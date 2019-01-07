package com.bootdo.common.service.impl;

import com.bootdo.common.aspect.LogAspect;
import com.bootdo.common.service.SettleAccountsJobService;
import com.bootdo.wx.dao.TaskdetailDao;
import com.bootdo.wx.dao.TaskinfoDao;
import com.bootdo.wx.domain.TaskinfoDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.util.Map;

@Service
public class SettleAccountsJobServiceImpl implements SettleAccountsJobService {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    TaskdetailDao taskdetailDao;
    @Autowired
    SettleAccountsJobService settleAccountsJobService;

    @Transactional
    @Override
    public void run(TaskinfoDO taskinfoDO) {
        try {
            // 查询分账
            List<Map<String,Object>> retMap = taskdetailDao.querySettleAccountDetail(taskinfoDO.getId());
            if(retMap.size()>0){
                for(int i=0;i<retMap.size();i++){


                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("com.bootdo.common.service.impl.SettleAccountsJobServiceImpl->run!message:{},cause:{},detail{}", e.getMessage(), e.getCause(), e.toString());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }
}
