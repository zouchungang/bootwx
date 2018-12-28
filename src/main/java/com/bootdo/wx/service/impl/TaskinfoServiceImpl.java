package com.bootdo.wx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.wx.dao.TaskinfoDao;
import com.bootdo.wx.domain.TaskinfoDO;
import com.bootdo.wx.service.TaskinfoService;



@Service
public class TaskinfoServiceImpl implements TaskinfoService {
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
	public int save(TaskinfoDO taskinfo){
		return taskinfoDao.save(taskinfo);
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
