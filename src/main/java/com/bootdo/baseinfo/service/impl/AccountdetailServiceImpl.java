package com.bootdo.baseinfo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.baseinfo.dao.AccountdetailDao;
import com.bootdo.baseinfo.domain.AccountdetailDO;
import com.bootdo.baseinfo.service.AccountdetailService;



@Service
public class AccountdetailServiceImpl implements AccountdetailService {
	@Autowired
	private AccountdetailDao accountdetailDao;
	
	@Override
	public AccountdetailDO get(Integer id){
		return accountdetailDao.get(id);
	}
	
	@Override
	public List<AccountdetailDO> list(Map<String, Object> map){
		return accountdetailDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return accountdetailDao.count(map);
	}
	
	@Override
	public int save(AccountdetailDO accountdetail){
		return accountdetailDao.save(accountdetail);
	}
	
	@Override
	public int update(AccountdetailDO accountdetail){
		return accountdetailDao.update(accountdetail);
	}
	
	@Override
	public int remove(Integer id){
		return accountdetailDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return accountdetailDao.batchRemove(ids);
	}
	
}
