package com.bootdo.baseinfo.service;

import com.bootdo.baseinfo.domain.ApplywithdrawinfoDO;

import java.util.List;
import java.util.Map;

/**
 * 提现申请
 * 
 * @author zcg
 *
 * @date 2018-12-27 10:51:15
 */
public interface ApplywithdrawinfoService {
	
	ApplywithdrawinfoDO get(Integer id);
	
	List<ApplywithdrawinfoDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ApplywithdrawinfoDO applywithdrawinfo);
	
	int update(ApplywithdrawinfoDO applywithdrawinfo);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
