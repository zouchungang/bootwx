package com.bootdo.system.service;

import com.bootdo.system.domain.ConfigDO;

import java.util.List;
import java.util.Map;

/**
 * 系统配置信息表
 * 
 * @author zcg
 * @email 804188877@qq.com
 * @date 2018-12-31 16:29:40
 */
public interface ConfigService {
	
	ConfigDO get(Long id);
	
	List<ConfigDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ConfigDO config);
	
	int update(ConfigDO config);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
