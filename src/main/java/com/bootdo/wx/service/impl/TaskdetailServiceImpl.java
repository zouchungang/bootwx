package com.bootdo.wx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.wx.dao.TaskdetailDao;
import com.bootdo.wx.domain.TaskdetailDO;
import com.bootdo.wx.service.TaskdetailService;



@Service
public class TaskdetailServiceImpl implements TaskdetailService {
	@Autowired
	private TaskdetailDao taskdetailDao;
	
	@Override
	public TaskdetailDO get(Integer id){
		return taskdetailDao.get(id);
	}
	
	@Override
	public List<TaskdetailDO> list(Map<String, Object> map){
		return taskdetailDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return taskdetailDao.count(map);
	}
	
	@Override
	public int save(TaskdetailDO taskdetail){
		return taskdetailDao.save(taskdetail);
	}
	
	@Override
	public int update(TaskdetailDO taskdetail){
		return taskdetailDao.update(taskdetail);
	}
	
	@Override
	public int remove(Integer id){
		return taskdetailDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return taskdetailDao.batchRemove(ids);
	}
	
}
