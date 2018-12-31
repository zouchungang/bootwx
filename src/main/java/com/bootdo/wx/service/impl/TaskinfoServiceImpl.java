package com.bootdo.wx.service.impl;

import com.bootdo.baseinfo.dao.WechatDao;
import com.bootdo.baseinfo.domain.WechatDO;
import com.bootdo.common.aspect.LogAspect;
import com.bootdo.system.dao.ConfigDao;
import com.bootdo.system.domain.ConfigDO;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bootdo.wx.dao.TaskinfoDao;
import com.bootdo.wx.domain.TaskinfoDO;
import com.bootdo.wx.service.TaskinfoService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;


@Service
public class TaskinfoServiceImpl implements TaskinfoService {
	private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
	@Autowired
	private TaskinfoDao taskinfoDao;
	@Autowired
	private WechatDao wechatDao;
	@Autowired
	private ConfigDao configDao;
	
	@Override
	public TaskinfoDO get(Integer id){
		return taskinfoDao.get(id);
	}
	
	@Override
	public List<TaskinfoDO> list(Map<String, Object> map){
		return taskinfoDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return taskinfoDao.count(map);
	}
	
	@Override
	@Transactional
	public int save(TaskinfoDO taskinfo){
		try {

			BigDecimal totalmoney = taskinfo.getPrice().multiply(new BigDecimal(taskinfo.getNum()));
			taskinfo.setTotalmoney(totalmoney);
			taskinfo.setStauts(9);
			taskinfoDao.save(taskinfo);
			// 1 是否有未开始的任务在排队
			Map<String,Object> taskMap = Maps.newHashMap();
			taskMap.put("stauts","1");
			List<TaskinfoDO> taskinfoListdb = taskinfoDao.list(taskMap);
			if(taskinfoListdb.size() != 0){ // 进入排队状态
				taskinfo.setStauts(1);
			}else {// 2 开始任务前，判断是否有足够的微信号, 冷却时间、当日上限数量、状态及绑定任务
				Map<String,Object> configMap = Maps.newHashMap();
				configMap.put("key","cdtime");
				List<ConfigDO> configDos = configDao.list(configMap);

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date now = new Date();
				Calendar nowTime = Calendar.getInstance();
				nowTime.add(Calendar.MINUTE, Integer.parseInt(configDos.get(0).getValue()));

				configMap.put("key","todaytaskquantity");
				configDos = configDao.list(configMap);
				Map<String,Object> wxMap = Maps.newHashMap();
				wxMap.put("lastdate",sdf.format(nowTime.getTime()));
				wxMap.put("todaytaskquantity",Integer.parseInt(configDos.get(0).getValue()));
				wxMap.put("limit",taskinfo.getNum());
				List<WechatDO> wechatListdb = wechatDao.list(wxMap);

				if(wechatListdb.size()==taskinfo.getNum()){// 有足够的微信号，开始将微信号绑定到任务上
					for(int i=0;i<=wechatListdb.size();i++){
						WechatDO wechatDO = wechatListdb.get(i);
						wechatDO.setTaskid(taskinfo.getId());
						wechatDao.update(wechatDO);
					}
				}
				// 绑定完任务，开始做任务 --------------------功能待开发-------------------------------

			}
			return taskinfoDao.update(taskinfo);
		}catch (Exception e){
			e.printStackTrace();
			logger.error("com.bootdo.wx.service.impl.TaskinfoServiceImpl.save->exception!message:{},cause:{},detail{}", e.getMessage(), e.getCause(), e.toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return -1;
		}
	}
	
	@Override
	public int update(TaskinfoDO taskinfo){
		return taskinfoDao.update(taskinfo);
	}
	
	@Override
	public int remove(Integer id){
		return taskinfoDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return taskinfoDao.batchRemove(ids);
	}
	
}
