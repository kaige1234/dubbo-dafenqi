package com.xl.es.data.doc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.xl.es.data.annotation.EsDoc;
import com.xl.es.data.annotation.EsField;
import com.xl.es.data.annotation.EsId;
import com.xl.es.data.annotation.FieldType;

/**
 * 商户对象
 * 
 * @author liufeng
 *
 */
@EsDoc(indexName = "xl", type = "merchant")
public class Merchant extends Document implements Serializable {
	@EsId
	@EsField(type=FieldType.Long)
	private Long lId;// id
	@EsField(type=FieldType.Long)
	private Long lUserId;//用户id
	@EsField(type=FieldType.text,analyze=true)
	private String strMerchantSn;//商户编号
	@EsField(type=FieldType.text,analyze=true)
	private String strName;// 商户名称
	@EsField(type=FieldType.text,analyze=true)
	private String strFullMerchantName;//商户全名称
	@EsField(type=FieldType.text,analyze=true)
	private String strTelephone;//电话
	@EsField(type=FieldType.text,analyze=true)
	private String strTelephone2;//电话
	@EsField(type=FieldType.Integer,analyze=true)
	private Integer nBusinessId;//一级行业
	@EsField(type=FieldType.text,analyze=true)
	private String strBusiness;//一级行业名称
	@EsField(type=FieldType.Integer,analyze=true)
	private Integer nBusiness2Id;//二级行业
	@EsField(type=FieldType.text,analyze=true)
	private String strBusiness2;//二级行业名称
	@EsField(type=FieldType.text,analyze=true)
	private String strBusinessLicenseSn;//营业执照编号
	@EsField(type=FieldType.text,analyze=true)
	private String strProvinceCode;//省编码
	@EsField(type=FieldType.text,analyze=true)
	private String strProvinceName;//省名称
	@EsField(type=FieldType.text,analyze=true)
	private String strCityCode;//市编码
	@EsField(type=FieldType.text,analyze=true)
	private String strCityName;//市名称
	@EsField(type=FieldType.text,analyze=true)
	private String strAreaCode;//区编码
	@EsField(type=FieldType.text,analyze=true)
	private String strAreaName;//区名称
	@EsField(type=FieldType.text,analyze=true)
	private String strStreetCode;//街道编码
	@EsField(type=FieldType.text,analyze=true)
	private String strStreetName;//街道名称
	@EsField(type=FieldType.Date,dateFormat="yyyy-MM-dd HH:mm:ss")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date dtAuditTime;//审核时间
	@EsField(type=FieldType.Date,dateFormat="yyyy-MM-dd HH:mm:ss",analyze=true)
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date dtFirstAuditTime;//初次上线时间
	@EsField(type=FieldType.Integer)
	private Integer nStatus;//状态 1是启用,2是停用
	@EsField(type=FieldType.text)
	private String strLongitude;//经度
	@EsField(type=FieldType.text)
	private String strLatitude;//纬度
	@EsField(type=FieldType.GeoPoint)
	private String strLongitudeAndLatitude;//经纬度
	@EsField(type=FieldType.Date,dateFormat="yyyy-MM-dd HH:mm:ss")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date dtCreateTime;//创建时间
	@EsField(type=FieldType.Date,dateFormat="yyyy-MM-dd HH:mm:ss")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date dtModifyTime;//更新时间
	@EsField(type=FieldType.text,analyze=true)
	private String strPayee;//收款人姓名
	@EsField(type=FieldType.Long)
	private Long lPayeeUserId;//支付人id
	@EsField(type=FieldType.Long)
	private Long lVirtualAccountId;//虚拟帐户id
	@EsField(type=FieldType.Long)
	private Long lShopKeeperUserId;//店主id
	@EsField(type=FieldType.text,analyze=true)
	private String strShopKeeperName;//店主名称
	@EsField(type=FieldType.text,analyze=true)
	private String strBusinessRange;//经营范围
	@EsField(type=FieldType.Long,analyze=true)
	private Long lHistoryAmount;//交易额
	@EsField(type=FieldType.Object)
	private List<Goods> goods=new ArrayList<Goods>();//商品信息
	public Long getlId() {
		return lId;
	}
	public void setlId(Long lId) {
		this.lId = lId;
	}
	public Long getlUserId() {
		return lUserId;
	}
	public void setlUserId(Long lUserId) {
		this.lUserId = lUserId;
	}
	public String getStrMerchantSn() {
		return strMerchantSn;
	}
	public void setStrMerchantSn(String strMerchantSn) {
		this.strMerchantSn = strMerchantSn;
	}
	public String getStrName() {
		return strName;
	}
	public void setStrName(String strName) {
		this.strName = strName;
	}
	public String getStrFullMerchantName() {
		return strFullMerchantName;
	}
	public void setStrFullMerchantName(String strFullMerchantName) {
		this.strFullMerchantName = strFullMerchantName;
	}
	public String getStrTelephone() {
		return strTelephone;
	}
	public void setStrTelephone(String strTelephone) {
		this.strTelephone = strTelephone;
	}
	public String getStrTelephone2() {
		return strTelephone2;
	}
	public void setStrTelephone2(String strTelephone2) {
		this.strTelephone2 = strTelephone2;
	}
	public Integer getnBusinessId() {
		return nBusinessId;
	}
	public void setnBusinessId(Integer nBusinessId) {
		this.nBusinessId = nBusinessId;
	}
	public String getStrBusiness() {
		return strBusiness;
	}
	public void setStrBusiness(String strBusiness) {
		this.strBusiness = strBusiness;
	}
	public Integer getnBusiness2Id() {
		return nBusiness2Id;
	}
	public void setnBusiness2Id(Integer nBusiness2Id) {
		this.nBusiness2Id = nBusiness2Id;
	}
	public String getStrBusiness2() {
		return strBusiness2;
	}
	public void setStrBusiness2(String strBusiness2) {
		this.strBusiness2 = strBusiness2;
	}
	public String getStrBusinessLicenseSn() {
		return strBusinessLicenseSn;
	}
	public void setStrBusinessLicenseSn(String strBusinessLicenseSn) {
		this.strBusinessLicenseSn = strBusinessLicenseSn;
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
	public String getStrStreetCode() {
		return strStreetCode;
	}
	public void setStrStreetCode(String strStreetCode) {
		this.strStreetCode = strStreetCode;
	}
	public String getStrStreetName() {
		return strStreetName;
	}
	public void setStrStreetName(String strStreetName) {
		this.strStreetName = strStreetName;
	}
	public Date getDtAuditTime() {
		return dtAuditTime;
	}
	public void setDtAuditTime(Date dtAuditTime) {
		this.dtAuditTime = dtAuditTime;
	}
	public Date getDtFirstAuditTime() {
		return dtFirstAuditTime;
	}
	public void setDtFirstAuditTime(Date dtFirstAuditTime) {
		this.dtFirstAuditTime = dtFirstAuditTime;
	}
	public Integer getnStatus() {
		return nStatus;
	}
	public void setnStatus(Integer nStatus) {
		this.nStatus = nStatus;
	}
	public String getStrLongitude() {
		return strLongitude;
	}
	public void setStrLongitude(String strLongitude) {
		this.strLongitude = strLongitude;
	}
	public String getStrLatitude() {
		return strLatitude;
	}
	public void setStrLatitude(String strLatitude) {
		this.strLatitude = strLatitude;
	}
	public String getStrLongitudeAndLatitude() {
		return strLongitudeAndLatitude;
	}
	public void setStrLongitudeAndLatitude(String strLongitudeAndLatitude) {
		this.strLongitudeAndLatitude = strLongitudeAndLatitude;
	}
	public Date getDtCreateTime() {
		return dtCreateTime;
	}
	public void setDtCreateTime(Date dtCreateTime) {
		this.dtCreateTime = dtCreateTime;
	}
	public Date getDtModifyTime() {
		return dtModifyTime;
	}
	public void setDtModifyTime(Date dtModifyTime) {
		this.dtModifyTime = dtModifyTime;
	}
	public String getStrPayee() {
		return strPayee;
	}
	public void setStrPayee(String strPayee) {
		this.strPayee = strPayee;
	}
	public Long getlPayeeUserId() {
		return lPayeeUserId;
	}
	public void setlPayeeUserId(Long lPayeeUserId) {
		this.lPayeeUserId = lPayeeUserId;
	}
	public Long getlVirtualAccountId() {
		return lVirtualAccountId;
	}
	public void setlVirtualAccountId(Long lVirtualAccountId) {
		this.lVirtualAccountId = lVirtualAccountId;
	}
	public Long getlShopKeeperUserId() {
		return lShopKeeperUserId;
	}
	public void setlShopKeeperUserId(Long lShopKeeperUserId) {
		this.lShopKeeperUserId = lShopKeeperUserId;
	}
	public String getStrShopKeeperName() {
		return strShopKeeperName;
	}
	public void setStrShopKeeperName(String strShopKeeperName) {
		this.strShopKeeperName = strShopKeeperName;
	}
	public List<Goods> getGoods() {
		return goods;
	}
	public void setGoods(List<Goods> goodsList) {
		this.goods = goodsList;
	}
	public String getStrBusinessRange() {
		return strBusinessRange;
	}
	public void setStrBusinessRange(String strBusinessRange) {
		this.strBusinessRange = strBusinessRange;
	}
	public Long getlHistoryAmount() {
		return lHistoryAmount;
	}
	public void setlHistoryAmount(Long lHistoryAmount) {
		this.lHistoryAmount = lHistoryAmount;
	}
}
