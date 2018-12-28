package com.bootdo.wx.dao;

import com.bootdo.wx.domain.TaskinfoDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 微信任务信息
 * @author zcg
 * @email 804188877@qq.com
 * @date 2018-12-27 17:36:15
 */
@Mapper
public interface TaskinfoDao {

	TaskinfoDO get(Integer id);
	
	List<TaskinfoDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(TaskinfoDO taskinfo);
	
	int update(TaskinfoDO taskinfo);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
