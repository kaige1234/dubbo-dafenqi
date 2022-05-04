package com.xl.cms.advertisement.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.xl.commons.data.BaseModel;

/**
 * 广告位
 * @author jianqinag.yuan
 */
@Table(name = "tbAdvertisementPosition")//广告位置维护表
public class AdvertisementPosition extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="nPositionCode")
	private Integer nPositionCode; // 位置编码

	@Column(name="strPositionCName")
	private String strPositionCName; // 位置名称

	@Column(name="nState")
	private String nState; // 状态：1启用2关闭3删除

	@Column(name="lModifyUserId")
	private Long lModifyUserId; // 最后编辑人ID

	@Column(name="strModifyUserName")
	private String strModifyUserName; // 最后编辑人姓名

	@Column(name="dtModifyTime")
	private Date dtModifyTime; // 最后编辑时间

	@Column(name="tsRefreshTime")
	private Date tsRefreshTime; //

	public Integer getnPositionCode() {
		return nPositionCode;
	}

	public void setnPositionCode(Integer nPositionCode) {
		this.nPositionCode = nPositionCode;
	}

	public String getStrPositionCName() {
		return strPositionCName;
	}

	public void setStrPositionCName(String strPositionCName) {
		this.strPositionCName = strPositionCName;
	}

	public String getnState() {
		return nState;
	}

	public void setnState(String nState) {
		this.nState = nState;
	}

	public Long getlModifyUserId() {
		return lModifyUserId;
	}

	public void setlModifyUserId(Long lModifyUserId) {
		this.lModifyUserId = lModifyUserId;
	}

	public String getStrModifyUserName() {
		return strModifyUserName;
	}

	public void setStrModifyUserName(String strModifyUserName) {
		this.strModifyUserName = strModifyUserName;
	}

	public Date getDtModifyTime() {
		return dtModifyTime;
	}

	public void setDtModifyTime(Date dtModifyTime) {
		this.dtModifyTime = dtModifyTime;
	}

	public Date getTsRefreshTime() {
		return tsRefreshTime;
	}

	public void setTsRefreshTime(Date tsRefreshTime) {
		this.tsRefreshTime = tsRefreshTime;
	}
	
	

}
