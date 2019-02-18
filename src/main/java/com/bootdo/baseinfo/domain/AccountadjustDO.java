package com.bootdo.baseinfo.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 账户资金冲减
 * 
 * @author zcg
 * @email 804188877@qq.com
 * @date 2019-01-15 15:22:42
 */
public class AccountadjustDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键
	private Integer id;
	//账户id
	private Integer aid;
	//用户账号
	private String username;
	//收支类型1.收入，2.支出
	private Integer isincome;
	//交易金额
	private BigDecimal dealmoney;
	//冲减原因
	private String remark;

	/**
	 * 设置：主键
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：主键
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：账户id
	 */
	public void setAid(Integer aid) {
		this.aid = aid;
	}
	/**
	 * 获取：账户id
	 */
	public Integer getAid() {
		return aid;
	}
	/**
	 * 设置：用户账号
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 获取：用户账号
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 设置：收支类型1.收入，2.支出
	 */
	public void setIsincome(Integer isincome) {
		this.isincome = isincome;
	}
	/**
	 * 获取：收支类型1.收入，2.支出
	 */
	public Integer getIsincome() {
		return isincome;
	}
	/**
	 * 设置：交易金额
	 */
	public void setDealmoney(BigDecimal dealmoney) {
		this.dealmoney = dealmoney;
	}
	/**
	 * 获取：交易金额
	 */
	public BigDecimal getDealmoney() {
		return dealmoney;
	}
	/**
	 * 设置：冲减原因
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：冲减原因
	 */
	public String getRemark() {
		return remark;
	}
}
