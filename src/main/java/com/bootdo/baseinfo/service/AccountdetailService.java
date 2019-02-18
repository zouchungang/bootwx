package com.bootdo.baseinfo.service;

import com.bootdo.baseinfo.domain.AccountdetailDO;

import java.util.List;
import java.util.Map;

/**
 * 资金账户明细
 * 
 * @author zcg
 *
 * @date 2018-12-27 10:51:15
 */
public interface AccountdetailService {
	
	AccountdetailDO get(Integer id);
	
	List<AccountdetailDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(AccountdetailDO accountdetail);
	
	int update(AccountdetailDO accountdetail);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
