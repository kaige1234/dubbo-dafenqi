package com.xl.cms.advertisement.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.xl.commons.data.BaseModel;

/**
 * 
 * 广告
 * @author jianqiang.yuan
 */
@Table(name = "tbAdvertisement")//广告信息表
public class Advertisement extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "strTitle")
	private String strTitle; // 广告标题
	
	@Column(name = "strShowTitle")
	private String strShowTitle; // 展现标题
	
	@Column(name = "strShowDesprivate")
	private String strShowDesprivate; // 展现描述
	
	@Column(name = "strActivityLabel")
	private String strActivityLabel; // 活动专区标签
	
	@Column(name = "nPositionCode")
	private Integer nPositionCode; // 广告位置编码
	
	@Column(name = "strPositionName")
	private String strPositionName; // 广告位置名称
	
	@Column(name = "nRegionType")
	private Integer nRegionType; // 地区：全局1地区2
	
	@Column(name = "strProvinceCode")
	private String strProvinceCode; // 省编码
	
	@Column(name = "strProvinceName")
	private String strProvinceName; // 省名称
	
	@Column(name = "strCityCode")
	private String strCityCode; // 市编码
	
	@Column(name = "strCityName")
	private String strCityName; // 市名称
	
	@Column(name = "strAreaCode")
	private String strAreaCode; // 区/县编码
	
	@Column(name = "strAreaName")
	private String strAreaName; // 区/县名称
	
	@Column(name = "strImgPath")
	private String strImgPath; // 图片地址
	
	@Column(name = "strHyperLinkUrl")
	private String strHyperLinkUrl; // 链接地址
	
	@Column(name = "nStateType")
	private Integer nStateType; // 状态：1启用2停用3删除
	
	@Column(name = "dtStartTime")
	private Date dtStartTime; // 预启时间
	
	@Column(name = "dtEndTime")
	private Date dtEndTime; // 过期时间
	
	@Column(name = "nSortNo")
	private Integer nSortNo; // 排序编号：启用的广告从1开始递增；停用的广告排序编号为-1
	
	@Column(name = "lModifyUserId")
	private Long lModifyUserId; // 最后编辑人ID
	
	@Column(name = "strModifyUserName")
	private String strModifyUserName; // 最后编辑人姓名
	
	@Column(name = "dtModifyTime")
	private Date dtModifyTime; // 最后编辑时间
	
	@Column(name = "tsRefreshTime")
	private Date tsRefreshTime; //

	public String getStrTitle() {
		return strTitle;
	}

	public void setStrTitle(String strTitle) {
		this.strTitle = strTitle;
	}

	public String getStrShowTitle() {
		return strShowTitle;
	}

	public void setStrShowTitle(String strShowTitle) {
		this.strShowTitle = strShowTitle;
	}

	public String getStrShowDesprivate() {
		return strShowDesprivate;
	}

	public void setStrShowDesprivate(String strShowDesprivate) {
		this.strShowDesprivate = strShowDesprivate;
	}

	public String getStrActivityLabel() {
		return strActivityLabel;
	}

	public void setStrActivityLabel(String strActivityLabel) {
		this.strActivityLabel = strActivityLabel;
	}

	public String getStrPositionName() {
		return strPositionName;
	}

	public void setStrPositionName(String strPositionName) {
		this.strPositionName = strPositionName;
	}

	public String getStrProvinceCode() {
		return strProvinceCode;
	}

	public void setStrProvinceCode(String strProvinceCode) {
		this.strProvinceCode = strProvinceCode;
	}

	public String getStrProvinceName() {
		return strProvinceName;
	}

	public void setStrProvinceName(String strProvinceName) {
		this.strProvinceName = strProvinceName;
	}

	public String getStrCityCode() {
		return strCityCode;
	}

	public void setStrCityCode(String strCityCode) {
		this.strCityCode = strCityCode;
	}

	public String getStrCityName() {
		return strCityName;
	}

	public void setStrCityName(String strCityName) {
		this.strCityName = strCityName;
	}

	public String getStrAreaCode() {
		return strAreaCode;
	}

	public void setStrAreaCode(String strAreaCode) {
		this.strAreaCode = strAreaCode;
	}

	public String getStrAreaName() {
		return strAreaName;
	}

	public void setStrAreaName(String strAreaName) {
		this.strAreaName = strAreaName;
	}

	public String getStrImgPath() {
		return strImgPath;
	}

	public void setStrImgPath(String strImgPath) {
		this.strImgPath = strImgPath;
	}

	public String getStrHyperLinkUrl() {
		return strHyperLinkUrl;
	}

	public void setStrHyperLinkUrl(String strHyperLinkUrl) {
		this.strHyperLinkUrl = strHyperLinkUrl;
	}

	public Integer getnPositionCode() {
		return nPositionCode;
	}

	public void setnPositionCode(Integer nPositionCode) {
		this.nPositionCode = nPositionCode;
	}

	public Integer getnRegionType() {
		return nRegionType;
	}

	public void setnRegionType(Integer nRegionType) {
		this.nRegionType = nRegionType;
	}

	public Integer getnStateType() {
		return nStateType;
	}

	public void setnStateType(Integer nStateType) {
		this.nStateType = nStateType;
	}

	public Date getDtStartTime() {
		return dtStartTime;
	}

	public void setDtStartTime(Date dtStartTime) {
		this.dtStartTime = dtStartTime;
	}

	public Date getDtEndTime() {
		return dtEndTime;
	}

	public void setDtEndTime(Date dtEndTime) {
		this.dtEndTime = dtEndTime;
	}

	public Integer getnSortNo() {
		return nSortNo;
	}

	public void setnSortNo(Integer nSortNo) {
		this.nSortNo = nSortNo;
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