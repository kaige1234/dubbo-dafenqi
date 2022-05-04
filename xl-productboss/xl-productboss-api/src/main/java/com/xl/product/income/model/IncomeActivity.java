package com.xl.product.income.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 收息活动
 * @author liufeng
 *
 */
@Table(name = "tbIncomeActivity")
public class IncomeActivity implements Serializable {

	@Id
	@Column(name="lId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long lId;//主键
	
	@Column(name="strName")
	private String strName;//活动名称
	
	@Column(name="strStartDate")
	private String strStartDate;//活动开始时间
	
	@Column(name="strEndDate")
	private String strEndDate;//活动结束时间
	
	@Column(name="lMoney")
	private Long lMoney;//满额(单位分)
	
	@Column(name="nInterest")
	private Integer nInterest;//利息折扣 
	
	@Column(name="strTerm")
	private String strTerm;//期数,多个以逗号分隔 
	
	@Column(name="strRange")
	private String strRange;// 应用范围  1:扫码支付  3:团购 多个以逗号分隔
	
	@Column(name="nStatus")
	private Integer nStatus;//活动状态:1:未发布  2:已发布  3:已下线
	
	@Column(name="nBear")
	private Integer nBear;//承担方(1:平台  2:商家)
	
	@Column(name="nBizType")
	private Integer nBizType;//业务分类  1:通用活动  2:收息商家活动  3:收息商品活动
	
	@Column(name="lCreatorId")
	private Long lCreatorId;//创建人ID
	
	@Column(name="strCreatorName")
	private String strCreatorName;//创建人
	
	@Column(name="dtCreatorTime")
	private String dtCreatorTime;//创建时间
	
	@Column(name="lModifierId")
	private Long lModifierId;//修改人ID
	
	@Column(name="strModifier")
	private String strModifier;//修改人
	
	@Column(name="dtModifierTime")
	private String dtModifierTime;//修改时间
	
	@Column(name="strDeptCode0")
	private String strDeptCode0;//0级组织机构编码
	
	@Column(name="strDeptCode1")
	private String strDeptCode1;//1级组织机构编码
	
	@Column(name="strDeptCode2")
	private String strDeptCode2;//2级组织机构编码
	
	@Column(name="strDeptCode3")
	private String strDeptCode3;//3级组织机构编码
	
	@Column(name="strDeptCode4")
	private String strDeptCode4;//4级组织机构编码
	
	@Column(name="strDeptCode5")
	private String strDeptCode5;//5级组织机构编码
	
	@Column(name="strDeptCode6")
	private String strDeptCode6;//6级组织机构编码
	
	@Column(name="strDeptCode7")
	private String strDeptCode7;//7级组织机构编码
	
	@Column(name="strDeptCode8")
	private String strDeptCode8;//8级组织机构编码
	
	@Column(name="strDeptCode9")
	private String strDeptCode9;//9级组织机构编码
	public Long getlId() {
		return lId;
	}
	public void setlId(Long lId) {
		this.lId = lId;
	}
	public String getStrName() {
		return strName;
	}
	public void setStrName(String strName) {
		this.strName = strName;
	}
	public String getStrStartDate() {
		return strStartDate;
	}
	public void setStrStartDate(String strStartDate) {
		this.strStartDate = strStartDate;
	}
	public String getStrEndDate() {
		return strEndDate;
	}
	public void setStrEndDate(String strEndDate) {
		this.strEndDate = strEndDate;
	}
	public Long getlMoney() {
		return lMoney;
	}
	public void setlMoney(Long lMoney) {
		this.lMoney = lMoney;
	}
	public Integer getnInterest() {
		return nInterest;
	}
	public void setnInterest(Integer nInterest) {
		this.nInterest = nInterest;
	}
	public String getStrTerm() {
		return strTerm;
	}
	public void setStrTerm(String strTerm) {
		this.strTerm = strTerm;
	}
	public String getStrRange() {
		return strRange;
	}
	public void setStrRange(String strRange) {
		this.strRange = strRange;
	}
	public Integer getnStatus() {
		return nStatus;
	}
	public void setnStatus(Integer nStatus) {
		this.nStatus = nStatus;
	}
	public Integer getnBear() {
		return nBear;
	}
	public void setnBear(Integer nBear) {
		this.nBear = nBear;
	}
	public Integer getnBizType() {
		return nBizType;
	}
	public void setnBizType(Integer nBizType) {
		this.nBizType = nBizType;
	}
	public Long getlCreatorId() {
		return lCreatorId;
	}
	public void setlCreatorId(Long lCreatorId) {
		this.lCreatorId = lCreatorId;
	}
	public String getStrCreatorName() {
		return strCreatorName;
	}
	public void setStrCreatorName(String strCreatorName) {
		this.strCreatorName = strCreatorName;
	}
	public String getDtCreatorTime() {
		return dtCreatorTime;
	}
	public void setDtCreatorTime(String dtCreatorTime) {
		this.dtCreatorTime = dtCreatorTime;
	}
	public Long getlModifierId() {
		return lModifierId;
	}
	public void setlModifierId(Long lModifierId) {
		this.lModifierId = lModifierId;
	}
	public String getStrModifier() {
		return strModifier;
	}
	public void setStrModifier(String strModifier) {
		this.strModifier = strModifier;
	}
	public String getDtModifierTime() {
		return dtModifierTime;
	}
	public void setDtModifierTime(String dtModifierTime) {
		this.dtModifierTime = dtModifierTime;
	}
	public String getStrDeptCode0() {
		return strDeptCode0;
	}
	public void setStrDeptCode0(String strDeptCode0) {
		this.strDeptCode0 = strDeptCode0;
	}
	public String getStrDeptCode1() {
		return strDeptCode1;
	}
	public void setStrDeptCode1(String strDeptCode1) {
		this.strDeptCode1 = strDeptCode1;
	}
	public String getStrDeptCode2() {
		return strDeptCode2;
	}
	public void setStrDeptCode2(String strDeptCode2) {
		this.strDeptCode2 = strDeptCode2;
	}
	public String getStrDeptCode3() {
		return strDeptCode3;
	}
	public void setStrDeptCode3(String strDeptCode3) {
		this.strDeptCode3 = strDeptCode3;
	}
	public String getStrDeptCode4() {
		return strDeptCode4;
	}
	public void setStrDeptCode4(String strDeptCode4) {
		this.strDeptCode4 = strDeptCode4;
	}
	public String getStrDeptCode5() {
		return strDeptCode5;
	}
	public void setStrDeptCode5(String strDeptCode5) {
		this.strDeptCode5 = strDeptCode5;
	}
	public String getStrDeptCode6() {
		return strDeptCode6;
	}
	public void setStrDeptCode6(String strDeptCode6) {
		this.strDeptCode6 = strDeptCode6;
	}
	public String getStrDeptCode7() {
		return strDeptCode7;
	}
	public void setStrDeptCode7(String strDeptCode7) {
		this.strDeptCode7 = strDeptCode7;
	}
	public String getStrDeptCode8() {
		return strDeptCode8;
	}
	public void setStrDeptCode8(String strDeptCode8) {
		this.strDeptCode8 = strDeptCode8;
	}
	public String getStrDeptCode9() {
		return strDeptCode9;
	}
	public void setStrDeptCode9(String strDeptCode9) {
		this.strDeptCode9 = strDeptCode9;
	}
	
	
}
