package com.bootdo.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.system.dao.ConfigDao;
import com.bootdo.system.domain.ConfigDO;
import com.bootdo.system.service.ConfigService;



@Service
public class ConfigServiceImpl implements ConfigService {
	@Autowired
	private ConfigDao configDao;
	
	@Override
	public ConfigDO get(Long id){
		return configDao.get(id);
	}
	
	@Override
	public List<ConfigDO> list(Map<String, Object> map){
		return configDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return configDao.count(map);
	}
	
	@Override
	public int save(ConfigDO config){
		return configDao.save(config);
	}
	
	@Override
	public int update(ConfigDO config){
		return configDao.update(config);
	}
	
	@Override
	public int remove(Long id){
		return configDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return configDao.batchRemove(ids);
	}
	
}
