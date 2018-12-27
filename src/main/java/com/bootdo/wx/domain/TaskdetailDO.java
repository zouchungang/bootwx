package com.bootdo.wx.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 任务执行明细信息
 * 
 * @author zcg
 * @email 804188877@qq.com
 * @date 2018-12-27 17:36:15
 */
public class TaskdetailDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//任务id
	private Integer taskid;
	//用户id
	private Integer uid;
	//父级用户id(邀请人id)
	private Integer parentid;
	//微信id
	private Integer wxid;
	//任务单价
	private BigDecimal price;
	//任务类型,1.阅读，2.点赞，3.关注
	private Integer tasktype;
	//状态 1.成功,2.失败
	private Integer stauts;
	//失败备注
	private String remark;
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
	 * 设置：父级用户id(邀请人id)
	 */
	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}
	/**
	 * 获取：父级用户id(邀请人id)
	 */
	public Integer getParentid() {
		return parentid;
	}
	/**
	 * 设置：微信id
	 */
	public void setWxid(Integer wxid) {
		this.wxid = wxid;
	}
	/**
	 * 获取：微信id
	 */
	public Integer getWxid() {
		return wxid;
	}
	/**
	 * 设置：任务单价
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取：任务单价
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * 设置：任务类型,1.阅读，2.点赞，3.关注
	 */
	public void setTasktype(Integer tasktype) {
		this.tasktype = tasktype;
	}
	/**
	 * 获取：任务类型,1.阅读，2.点赞，3.关注
	 */
	public Integer getTasktype() {
		return tasktype;
	}
	/**
	 * 设置：状态 1.成功,2.失败
	 */
	public void setStauts(Integer stauts) {
		this.stauts = stauts;
	}
	/**
	 * 获取：状态 1.成功,2.失败
	 */
	public Integer getStauts() {
		return stauts;
	}
	/**
	 * 设置：失败备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：失败备注
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
}
