package com.bootdo.wx.service.impl;

import com.bootdo.common.aspect.LogAspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
			return taskinfoDao.save(taskinfo);
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
