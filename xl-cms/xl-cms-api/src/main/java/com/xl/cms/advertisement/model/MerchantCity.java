package com.xl.cms.advertisement.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.xl.commons.data.BaseModel;

/**
 * 收息活动
 * 
 * @author liufeng
 *
 */
@Table(name = "tbMerchantCity")
public class MerchantCity extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "strName")
	private String strName; // 城市名称
	@Column(name = "strCode")
	private String strCode; // 城市代码
	@Column(name = "strParentCode")
	private String strParentCode; // 父编码
	@Column(name = "nLevel")
	private Integer nLevel; // 层级 1:省:2市
	@Column(name = "strNameEn")
	private String strNameEn; // 城市英文
	@Column(name = "strShortName")
	private String strShortName; // 城市短名称
	@Column(name = "nState")
	private Integer nState; // 0-未发布，1-发布
	@Column(name = "nRecommendType")
	private Integer nRecommendType; // 是否推荐：0：不推荐 1：推荐
	@Column(name = "nRecommendSort")
	private Integer nRecommendSort; // 推荐城市排序值
	@Column(name = "lModifierId")
	private Long lModifierId; // 更新人ID
	@Column(name = "strModifierName")
	private String strModifierName; // 更改人名称
	@Column(name = "dtModifierTime")
	private Date dtModifierTime; // 更新时间

	public String getStrName() {
		return strName;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}

	public String getStrCode() {
		return strCode;
	}

	public void setStrCode(String strCode) {
		this.strCode = strCode;
	}

	public String getStrParentCode() {
		return strParentCode;
	}

	public void setStrParentCode(String strParentCode) {
		this.strParentCode = strParentCode;
	}

	public Integer getnLevel() {
		return nLevel;
	}

	public void setnLevel(Integer nLevel) {
		this.nLevel = nLevel;
	}

	public String getStrNameEn() {
		return strNameEn;
	}

	public void setStrNameEn(String strNameEn) {
		this.strNameEn = strNameEn;
	}

	public String getStrShortName() {
		return strShortName;
	}

	public void setStrShortName(String strShortName) {
		this.strShortName = strShortName;
	}

	public Integer getnState() {
		return nState;
	}

	public void setnState(Integer nState) {
		this.nState = nState;
	}

	public Integer getnRecommendType() {
		return nRecommendType;
	}

	public void setnRecommendType(Integer nRecommendType) {
		this.nRecommendType = nRecommendType;
	}

	public Integer getnRecommendSort() {
		return nRecommendSort;
	}

	public void setnRecommendSort(Integer nRecommendSort) {
		this.nRecommendSort = nRecommendSort;
	}

	public Long getlModifierId() {
		return lModifierId;
	}

	public void setlModifierId(Long lModifierId) {
		this.lModifierId = lModifierId;
	}

	public String getStrModifierName() {
		return strModifierName;
	}

	public void setStrModifierName(String strModifierName) {
		this.strModifierName = strModifierName;
	}

	public Date getDtModifierTime() {
		return dtModifierTime;
	}

	public void setDtModifierTime(Date dtModifierTime) {
		this.dtModifierTime = dtModifierTime;
	}

}
