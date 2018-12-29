package com.bootdo.baseinfo.service.impl;

import com.bootdo.baseinfo.dao.AccountDao;
import com.bootdo.baseinfo.domain.AccountDO;
import com.bootdo.common.aspect.LogAspect;
import com.bootdo.common.exception.BDException;
import com.bootdo.common.utils.R;
import com.bootdo.system.dao.UserDao;
import com.bootdo.util.MessagesCode;
import com.bootdo.util.MsgUtil;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.bootdo.baseinfo.dao.ApplywithdrawinfoDao;
import com.bootdo.baseinfo.domain.ApplywithdrawinfoDO;
import com.bootdo.baseinfo.service.ApplywithdrawinfoService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;


@Service
public class ApplywithdrawinfoServiceImpl implements ApplywithdrawinfoService {
	private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
	@Autowired
	private ApplywithdrawinfoDao applywithdrawinfoDao;
	@Autowired
	private AccountDao accountDao;
	
	@Override
	public ApplywithdrawinfoDO get(Integer id){
		return applywithdrawinfoDao.get(id);
	}
	
	@Override
	public List<ApplywithdrawinfoDO> list(Map<String, Object> map){
		return applywithdrawinfoDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return applywithdrawinfoDao.count(map);
	}
	
	@Override
	@Transactional
	public R save(ApplywithdrawinfoDO applywithdrawinfo) {
		//用户申请提现，  申请提现 = 申请提现 + 操作的申请提现金额，可用金额 = 可用金额 – 操作金额
		try {
			Map<String,Object> paramMap = Maps.newHashMap();
			paramMap.put("uid",applywithdrawinfo.getUid());
			// 用户账户查询
			List<AccountDO> accountDOs = accountDao.list(paramMap);
			AccountDO accountDO = accountDOs.get(0);
            if(applywithdrawinfo.getApplymoney().compareTo(accountDO.getUsemoney()) == 1){
				return R.error(1, MsgUtil.getMsg(MessagesCode.ERROR_CODE_1001));
            }
			accountDO.setApplywithdrawmoney(accountDO.getApplywithdrawmoney().add(applywithdrawinfo.getApplymoney()));
			accountDO.setUsemoney(accountDO.getUsemoney().subtract(applywithdrawinfo.getApplymoney()));
			accountDao.save(accountDO);
			applywithdrawinfo.setStauts(1);
			applywithdrawinfoDao.save(applywithdrawinfo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("com.bootdo.baseinfo.service.impl.ApplywithdrawinfoServiceImpl.save->exception!message:{},cause:{},detail{}", e.getMessage(), e.getCause(), e.toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return R.ok();
	}
	
	@Override
	@Transactional
	public int update(ApplywithdrawinfoDO applywithdrawinfo){
		int ret = -1;
		try {
			ApplywithdrawinfoDO paramDO = new ApplywithdrawinfoDO();
			paramDO.setId(applywithdrawinfo.getId());
            paramDO.setStauts(applywithdrawinfo.getStauts());
            paramDO.setRemark(applywithdrawinfo.getRemark());
            Map<String,Object> paramMap = Maps.newHashMap();
            paramMap.put("uid",applywithdrawinfo.getUid());
			// 用户账户查询
			List<AccountDO> accountDOs = accountDao.list(paramMap);
			AccountDO accountDO = accountDOs.get(0);
			// 同意并支付
			if(applywithdrawinfo.getStauts().toString()=="2"){
				accountDO.setApplywithdrawmoney(accountDO.getApplywithdrawmoney().subtract(applywithdrawinfo.getApplymoney()));
				accountDO.setTotalwithdrawmoney(accountDO.getTotalwithdrawmoney().add(applywithdrawinfo.getApplymoney()));
			}else if(applywithdrawinfo.getStauts().toString()=="3"){ // 审核拒绝
				accountDO.setApplywithdrawmoney(accountDO.getApplywithdrawmoney().subtract(applywithdrawinfo.getApplymoney()));
				accountDO.setUsemoney(accountDO.getUsemoney().add(applywithdrawinfo.getApplymoney()));
			}else {
				return ret;
			}
			accountDao.save(accountDO);
			ret = applywithdrawinfoDao.update(paramDO);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("com.bootdo.baseinfo.service.impl.ApplywithdrawinfoServiceImpl.update->exception!message:{},cause:{},detail{}", e.getMessage(), e.getCause(), e.toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return ret;
	}
	
	@Override
	@Transactional
	public int remove(Integer id){
		int ret = -1;
		try {
			ApplywithdrawinfoDO applywithdrawinfo = applywithdrawinfoDao.get(id);
			Map<String,Object> paramMap = Maps.newHashMap();
			paramMap.put("uid",applywithdrawinfo.getUid());
			// 用户账户查询
			List<AccountDO> accountDOs = accountDao.list(paramMap);
			AccountDO accountDO = accountDOs.get(0);
			accountDO.setApplywithdrawmoney(accountDO.getApplywithdrawmoney().subtract(applywithdrawinfo.getApplymoney()));
			accountDO.setUsemoney(accountDO.getUsemoney().add(applywithdrawinfo.getApplymoney()));
			accountDao.save(accountDO);
			ret = applywithdrawinfoDao.remove(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("com.bootdo.baseinfo.service.impl.ApplywithdrawinfoServiceImpl.remove->exception!message:{},cause:{},detail{}", e.getMessage(), e.getCause(), e.toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return ret;
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		//return applywithdrawinfoDao.batchRemove(ids);
		return -1;
	}
	
}
