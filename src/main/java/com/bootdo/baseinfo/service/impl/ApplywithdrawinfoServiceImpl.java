package com.bootdo.baseinfo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.baseinfo.dao.ApplywithdrawinfoDao;
import com.bootdo.baseinfo.domain.ApplywithdrawinfoDO;
import com.bootdo.baseinfo.service.ApplywithdrawinfoService;



@Service
public class ApplywithdrawinfoServiceImpl implements ApplywithdrawinfoService {
	@Autowired
	private ApplywithdrawinfoDao applywithdrawinfoDao;
	
	@Override
	public ApplywithdrawinfoDO get(Integer id){
		return applywithdrawinfoDao.get(id);
	}
	
	@Override
	public List<ApplywithdrawinfoDO> list(Map<String, Object> map){
		return applywithdrawinfoDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return applywithdrawinfoDao.count(map);
	}
	
	@Override
	public int save(ApplywithdrawinfoDO applywithdrawinfo){
		return applywithdrawinfoDao.save(applywithdrawinfo);
	}
	
	@Override
	public int update(ApplywithdrawinfoDO applywithdrawinfo){
		return applywithdrawinfoDao.update(applywithdrawinfo);
	}
	
	@Override
	public int remove(Integer id){
		return applywithdrawinfoDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return applywithdrawinfoDao.batchRemove(ids);
	}
	
}
