package com.bootdo.wx.dao;

import com.bootdo.wx.domain.TaskdetailDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 任务执行明细信息
 * @author zcg
 * @email 804188877@qq.com
 * @date 2018-12-27 17:36:15
 */
@Mapper
public interface TaskdetailDao {

	TaskdetailDO get(Integer id);
	
	List<TaskdetailDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(TaskdetailDO taskdetail);
	
	int update(TaskdetailDO taskdetail);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
