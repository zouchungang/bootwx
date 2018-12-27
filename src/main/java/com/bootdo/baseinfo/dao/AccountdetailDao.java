package com.bootdo.baseinfo.dao;

import com.bootdo.baseinfo.domain.AccountdetailDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 资金账户明细
 * @author zcg
 *
 * @date 2018-12-27 10:51:15
 */
@Mapper
public interface AccountdetailDao {

	AccountdetailDO get(Integer id);
	
	List<AccountdetailDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(AccountdetailDO accountdetail);
	
	int update(AccountdetailDO accountdetail);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
