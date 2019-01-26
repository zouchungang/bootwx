package com.bootdo.baseinfo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.baseinfo.dao.AccountadjustDao;
import com.bootdo.baseinfo.domain.AccountadjustDO;
import com.bootdo.baseinfo.service.AccountadjustService;



@Service
public class AccountadjustServiceImpl implements AccountadjustService {
	@Autowired
	private AccountadjustDao accountadjustDao;
	
	@Override
	public AccountadjustDO get(Integer id){
		return accountadjustDao.get(id);
	}
	
	@Override
	public List<AccountadjustDO> list(Map<String, Object> map){
		return accountadjustDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return accountadjustDao.count(map);
	}
	
	@Override
	public int save(AccountadjustDO accountadjust){
		return accountadjustDao.save(accountadjust);
	}
	
	@Override
	public int update(AccountadjustDO accountadjust){
		return accountadjustDao.update(accountadjust);
	}
	
	@Override
	public int remove(Integer id){
		return accountadjustDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return accountadjustDao.batchRemove(ids);
	}
	
}
