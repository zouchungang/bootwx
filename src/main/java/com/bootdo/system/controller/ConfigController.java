package com.bootdo.system.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootdo.system.domain.ConfigDO;
import com.bootdo.system.service.ConfigService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 系统配置信息表
 * 
 * @author zcg
 * @email 804188877@qq.com
 * @date 2018-12-31 16:29:40
 */
 
@Controller
@RequestMapping("/system/config")
public class ConfigController {
	@Autowired
	private ConfigService configService;
	
	@GetMapping()
		@RequiresPermissions("system:config:config")
	String Config(){
	    return "system/config/config";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:config:config")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ConfigDO> configList = configService.list(query);
		int total = configService.count(query);
		PageUtils pageUtils = new PageUtils(configList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:config:add")
	String add(){
	    return "system/config/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:config:edit")
	String edit(@PathVariable("id") Long id,Model model){
		ConfigDO config = configService.get(id);
		model.addAttribute("config", config);
	    return "system/config/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:config:add")
	public R save( ConfigDO config){
		if(configService.save(config)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:config:edit")
	public R update( ConfigDO config){
		configService.update(config);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:config:remove")
	public R remove( Long id){
		if(configService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:config:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		configService.batchRemove(ids);
		return R.ok();
	}
	
}
