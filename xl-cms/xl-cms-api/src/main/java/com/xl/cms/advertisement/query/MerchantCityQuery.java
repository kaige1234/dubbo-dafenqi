package com.xl.cms.advertisement.query;

import javax.persistence.Column;

import com.xl.cms.advertisement.model.MerchantCity;

/**
 * 城市编码查询
 * 
 * @author jianqinag.yuan
 */
public class MerchantCityQuery extends MerchantCity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name="strProvinceCode")
	private String strProvinceCode;  // 省 编码
	@Column(name="strProvinceName")
	private String strProvinceName;  // 省 名称
	@Column(name="strCityCode")
	private String strCityCode;      // 市 编码
	@Column(name="strCityName")
	private String strCityName;      // 市 名称
	@Column(name="strAreaCode")
	private String strAreaCode;      // 区 编码
	@Column(name="strAreaName")
	private String strAreaName;      // 区 名称

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
}
