package com.bootdo.baseinfo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.baseinfo.dao.AccountDao;
import com.bootdo.baseinfo.domain.AccountDO;
import com.bootdo.baseinfo.service.AccountService;



@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountDao accountDao;
	
	@Override
	public AccountDO get(Integer id){
		return accountDao.get(id);
	}
	
	@Override
	public List<AccountDO> list(Map<String, Object> map){
		return accountDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return accountDao.count(map);
	}
	
	@Override
	public int save(AccountDO account){
		return accountDao.save(account);
	}
	
	@Override
	public int update(AccountDO account){
		return accountDao.update(account);
	}
	
	@Override
	public int remove(Integer id){
		return accountDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return accountDao.batchRemove(ids);
	}
	
}
