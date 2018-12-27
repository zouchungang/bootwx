package com.bootdo.baseinfo.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 资金账户明细
 * 
 * @author zcg
 *
 * @date 2018-12-27 10:51:15
 */
public class AccountdetailDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//账户id
	private Integer aid;
	//操作类型,1.结算,3.提现
	private Integer operatetype;
	//收支类型,1.收入，2.支出
	private Integer isincome;
	//交易前余额
	private BigDecimal frontaccount;
	//交易金额
	private BigDecimal dealmoney;
	//交易后余额
	private BigDecimal backaccount;
	//创建时间
	private Date createdate;
	//修改时间
	private Date modifydate;

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
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
	 * 设置：操作类型,1.结算,3.提现
	 */
	public void setOperatetype(Integer operatetype) {
		this.operatetype = operatetype;
	}
	/**
	 * 获取：操作类型,1.结算,3.提现
	 */
	public Integer getOperatetype() {
		return operatetype;
	}
	/**
	 * 设置：收支类型,1.收入，2.支出
	 */
	public void setIsincome(Integer isincome) {
		this.isincome = isincome;
	}
	/**
	 * 获取：收支类型,1.收入，2.支出
	 */
	public Integer getIsincome() {
		return isincome;
	}
	/**
	 * 设置：交易前余额
	 */
	public void setFrontaccount(BigDecimal frontaccount) {
		this.frontaccount = frontaccount;
	}
	/**
	 * 获取：交易前余额
	 */
	public BigDecimal getFrontaccount() {
		return frontaccount;
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
	 * 设置：交易后余额
	 */
	public void setBackaccount(BigDecimal backaccount) {
		this.backaccount = backaccount;
	}
	/**
	 * 获取：交易后余额
	 */
	public BigDecimal getBackaccount() {
		return backaccount;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreatedate() {
		return createdate;
	}
	/**
	 * 设置：修改时间
	 */
	public void setModifydate(Date modifydate) {
		this.modifydate = modifydate;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getModifydate() {
		return modifydate;
	}
}
