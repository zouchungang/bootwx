package com.bootdo.baseinfo.controller;

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

import com.bootdo.baseinfo.domain.AccountdetailDO;
import com.bootdo.baseinfo.service.AccountdetailService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 资金账户明细
 * 
 * @author zcg
 *
 * @date 2018-12-27 10:51:15
 */
 
@Controller
@RequestMapping("/baseinfo/accountdetail")
public class AccountdetailController {
	@Autowired
	private AccountdetailService accountdetailService;
	
	@GetMapping()
	@RequiresPermissions("baseinfo:accountdetail:accountdetail")
	String Accountdetail(){
	    return "baseinfo/accountdetail/accountdetail";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("baseinfo:accountdetail:accountdetail")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<AccountdetailDO> accountdetailList = accountdetailService.list(query);
		int total = accountdetailService.count(query);
		PageUtils pageUtils = new PageUtils(accountdetailList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("baseinfo:accountdetail:add")
	String add(){
	    return "baseinfo/accountdetail/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("baseinfo:accountdetail:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		AccountdetailDO accountdetail = accountdetailService.get(id);
		model.addAttribute("accountdetail", accountdetail);
	    return "baseinfo/accountdetail/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("baseinfo:accountdetail:add")
	public R save( AccountdetailDO accountdetail){
		if(accountdetailService.save(accountdetail)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("baseinfo:accountdetail:edit")
	public R update( AccountdetailDO accountdetail){
		accountdetailService.update(accountdetail);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("baseinfo:accountdetail:remove")
	public R remove( Integer id){
		if(accountdetailService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("baseinfo:accountdetail:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		accountdetailService.batchRemove(ids);
		return R.ok();
	}
	
}
