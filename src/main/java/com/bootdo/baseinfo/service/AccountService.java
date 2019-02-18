package com.bootdo.baseinfo.service;

import com.bootdo.baseinfo.domain.AccountDO;

import java.util.List;
import java.util.Map;

/**
 * 用户账户信息
 * 
 * @author zcg
 *
 * @date 2018-12-27 10:51:15
 */
public interface AccountService {
	
	AccountDO get(Integer id);
	
	List<AccountDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(AccountDO account);
	
	int update(AccountDO account);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
