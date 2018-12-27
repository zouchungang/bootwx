package com.bootdo.baseinfo.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 微信号信息
 * 
 * @author zcg
 *
 * @date 2018-12-27 10:51:16
 */
public class WechatDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//用户id
	private Integer uid;
	//微信号
	private String wechat;
	//微信密码
	private String password;
	//62数据
	private String data62;
	//最近一次做任务时间
	private Date lastdate;
	//累计任务次数
	private Integer totaltaskquantity;
	//今日任务次数
	private Integer todaytaskquantity;
	//状态 1.启用,2.停用,3.占用
	private Integer stauts;
	//微信状态备注
	private String remark;
	//创建时间
	private Date createdate;
	//修改时间
	private Date modifydate;
	//任务id
	private Integer taskid;

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
	 * 设置：用户id
	 */
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	/**
	 * 获取：用户id
	 */
	public Integer getUid() {
		return uid;
	}
	/**
	 * 设置：微信号
	 */
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	/**
	 * 获取：微信号
	 */
	public String getWechat() {
		return wechat;
	}
	/**
	 * 设置：微信密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：微信密码
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置：62数据
	 */
	public void setData62(String data62) {
		this.data62 = data62;
	}
	/**
	 * 获取：62数据
	 */
	public String getData62() {
		return data62;
	}
	/**
	 * 设置：最近一次做任务时间
	 */
	public void setLastdate(Date lastdate) {
		this.lastdate = lastdate;
	}
	/**
	 * 获取：最近一次做任务时间
	 */
	public Date getLastdate() {
		return lastdate;
	}
	/**
	 * 设置：累计任务次数
	 */
	public void setTotaltaskquantity(Integer totaltaskquantity) {
		this.totaltaskquantity = totaltaskquantity;
	}
	/**
	 * 获取：累计任务次数
	 */
	public Integer getTotaltaskquantity() {
		return totaltaskquantity;
	}
	/**
	 * 设置：今日任务次数
	 */
	public void setTodaytaskquantity(Integer todaytaskquantity) {
		this.todaytaskquantity = todaytaskquantity;
	}
	/**
	 * 获取：今日任务次数
	 */
	public Integer getTodaytaskquantity() {
		return todaytaskquantity;
	}
	/**
	 * 设置：状态 1.启用,2.停用,3.占用
	 */
	public void setStauts(Integer stauts) {
		this.stauts = stauts;
	}
	/**
	 * 获取：状态 1.启用,2.停用,3.占用
	 */
	public Integer getStauts() {
		return stauts;
	}
	/**
	 * 设置：微信状态备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：微信状态备注
	 */
	public String getRemark() {
		return remark;
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
	/**
	 * 设置：任务id
	 */
	public void setTaskid(Integer taskid) {
		this.taskid = taskid;
	}
	/**
	 * 获取：任务id
	 */
	public Integer getTaskid() {
		return taskid;
	}
}
