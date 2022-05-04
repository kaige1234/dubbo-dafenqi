package com.xl.es.data.doc;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.xl.es.data.annotation.EsField;
import com.xl.es.data.annotation.EsId;
import com.xl.es.data.annotation.FieldType;

public class Goods {
	@EsId
	@EsField(type = FieldType.Long)
	private Long lId;// 主键ID
	@EsField(type = FieldType.text,analyze=true)
	private String strGoodsName;// 商品名称
	@EsField(type = FieldType.Long)
	private Long lGoodsPrice;// 商品单价(单位:分)
	@EsField(type = FieldType.Integer)
	private Integer nGoodsStock;// 商品库存
	@EsField(type = FieldType.text,analyze=true)
	private String strTicketName;// 卡券名称
	@EsField(type = FieldType.Integer)
	private Integer nTicketCount;// 卡券数量
	private Long lGoodsTotalPrice;// 商品总价(单位:分)
	@EsField(type = FieldType.Long)
	private Long lOrginPrice;// 商品原价(单位:分)
	@EsField(type = FieldType.Integer)
	private Integer nGoodsStatus;// 商品状态,0:未上架,1:已上架,2:已下架
	@EsField(type = FieldType.Long)
	private Long lReceiptMerchantId;// 收款商户ID
	@EsField(type = FieldType.text,analyze=true)
	private String strReceiptMerchantName;// 收款商户名称
	@EsField(type = FieldType.Long)
	private Long lVirtualAccountId;// 商户虚拟账户id
	@EsField(type = FieldType.Integer)
	private Integer nGoodsDateType;// 商品有效期类型,0:长期有效,1:固定日期
	@EsField(type = FieldType.Date, dateFormat = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date strGoodsStartDate;// 商品有效开始时间
	@EsField(type = FieldType.Date, dateFormat = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date strGoodsEndDate;// 商品有效结束时间
	@EsField(type = FieldType.Long)
	private Long lCreatorId;// 创建人ID
	@EsField(type = FieldType.Date, dateFormat = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date dtCreateTime;// 创建时间
	@EsField(type = FieldType.Long)
	private Long lModifierId;// 更新人id
	@EsField(type = FieldType.Date, dateFormat = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date dtModifierTime;// 更新时间
	@EsField(type = FieldType.Integer,analyze=true)
	private Integer nGoodsType;// 消费类型 1:平台 2:一卡通 3:团购
	@EsField(type = FieldType.Integer)
	private Integer nSoldStock;// 已售库存
	@EsField(type = FieldType.text)
	private String strShowPrice;// 显示价格
	@EsField(type = FieldType.Integer)
	private Integer nGoodsStockLimit;// 限量发售
	@EsField(type = FieldType.Integer)
	private Integer nBuyLimitType;// 购买限制类型 1:每天 2:每周 3:每月 4:活动销售期内
	@EsField(type = FieldType.text,analyze=true)
	private String strProvinceCodeMerchant;// 收款商家所在省编码
	@EsField(type = FieldType.text,analyze=true)
	private String strProvinceNameMerchant;// 收款商家所在省名称
	@EsField(type = FieldType.text,analyze=true)
	private String strCityCodeMerchant;// 收款商家所在城市编码
	@EsField(type = FieldType.text,analyze=true)
	private String strCityNameMerchant;// 收款商家所在城市名称
	@EsField(type = FieldType.Long)
	private Long lSalesWeight;// 销量权重;
	@EsField(type = FieldType.Long)
	private Long lNewWeight;// 当月新增权重
	@EsField(type = FieldType.Long)
	private Long lActivityWeight;// 立减活动权重
	@EsField(type = FieldType.Long)
	private Long lDistanceWeight;// 团购距离权重
	@EsField(type = FieldType.Long)
	private Long lExpenseWeight;// 同类消费推荐权重
	@EsField(type = FieldType.Long)
	private Long lWeightCount;// 权重总分

	public Long getlId() {
		return lId;
	}

	public void setlId(Long lId) {
		this.lId = lId;
	}

	public String getStrGoodsName() {
		return strGoodsName;
	}

	public void setStrGoodsName(String strGoodsName) {
		this.strGoodsName = strGoodsName;
	}

	public Long getlGoodsPrice() {
		return lGoodsPrice;
	}

	public void setlGoodsPrice(Long lGoodsPrice) {
		this.lGoodsPrice = lGoodsPrice;
	}

	public Integer getnGoodsStock() {
		return nGoodsStock;
	}

	public void setnGoodsStock(Integer nGoodsStock) {
		this.nGoodsStock = nGoodsStock;
	}

	public String getStrTicketName() {
		return strTicketName;
	}

	public void setStrTicketName(String strTicketName) {
		this.strTicketName = strTicketName;
	}

	public Integer getnTicketCount() {
		return nTicketCount;
	}

	public void setnTicketCount(Integer nTicketCount) {
		this.nTicketCount = nTicketCount;
	}

	public Long getlGoodsTotalPrice() {
		return lGoodsTotalPrice;
	}

	public void setlGoodsTotalPrice(Long lGoodsTotalPrice) {
		this.lGoodsTotalPrice = lGoodsTotalPrice;
	}

	public Long getlOrginPrice() {
		return lOrginPrice;
	}

	public void setlOrginPrice(Long lOrginPrice) {
		this.lOrginPrice = lOrginPrice;
	}

	public Integer getnGoodsStatus() {
		return nGoodsStatus;
	}

	public void setnGoodsStatus(Integer nGoodsStatus) {
		this.nGoodsStatus = nGoodsStatus;
	}

	public Long getlReceiptMerchantId() {
		return lReceiptMerchantId;
	}

	public void setlReceiptMerchantId(Long lReceiptMerchantId) {
		this.lReceiptMerchantId = lReceiptMerchantId;
	}

	public String getStrReceiptMerchantName() {
		return strReceiptMerchantName;
	}

	public void setStrReceiptMerchantName(String strReceiptMerchantName) {
		this.strReceiptMerchantName = strReceiptMerchantName;
	}

	public Long getlVirtualAccountId() {
		return lVirtualAccountId;
	}

	public void setlVirtualAccountId(Long lVirtualAccountId) {
		this.lVirtualAccountId = lVirtualAccountId;
	}

	public Integer getnGoodsDateType() {
		return nGoodsDateType;
	}

	public void setnGoodsDateType(Integer nGoodsDateType) {
		this.nGoodsDateType = nGoodsDateType;
	}

	public Date getStrGoodsStartDate() {
		return strGoodsStartDate;
	}

	public void setStrGoodsStartDate(Date strGoodsStartDate) {
		this.strGoodsStartDate = strGoodsStartDate;
	}

	public Date getStrGoodsEndDate() {
		return strGoodsEndDate;
	}

	public void setStrGoodsEndDate(Date strGoodsEndDate) {
		this.strGoodsEndDate = strGoodsEndDate;
	}

	public Long getlCreatorId() {
		return lCreatorId;
	}

	public void setlCreatorId(Long lCreatorId) {
		this.lCreatorId = lCreatorId;
	}

	public Date getDtCreateTime() {
		return dtCreateTime;
	}

	public void setDtCreateTime(Date dtCreateTime) {
		this.dtCreateTime = dtCreateTime;
	}

	public Long getlModifierId() {
		return lModifierId;
	}

	public void setlModifierId(Long lModifierId) {
		this.lModifierId = lModifierId;
	}

	public Date getDtModifierTime() {
		return dtModifierTime;
	}

	public void setDtModifierTime(Date dtModifierTime) {
		this.dtModifierTime = dtModifierTime;
	}

	public Integer getnGoodsType() {
		return nGoodsType;
	}

	public void setnGoodsType(Integer nGoodsType) {
		this.nGoodsType = nGoodsType;
	}

	public Integer getnSoldStock() {
		return nSoldStock;
	}

	public void setnSoldStock(Integer nSoldStock) {
		this.nSoldStock = nSoldStock;
	}

	public String getStrShowPrice() {
		return strShowPrice;
	}

	public void setStrShowPrice(String strShowPrice) {
		this.strShowPrice = strShowPrice;
	}

	public Integer getnGoodsStockLimit() {
		return nGoodsStockLimit;
	}

	public void setnGoodsStockLimit(Integer nGoodsStockLimit) {
		this.nGoodsStockLimit = nGoodsStockLimit;
	}

	public Integer getnBuyLimitType() {
		return nBuyLimitType;
	}

	public void setnBuyLimitType(Integer nBuyLimitType) {
		this.nBuyLimitType = nBuyLimitType;
	}

	public String getStrProvinceCodeMerchant() {
		return strProvinceCodeMerchant;
	}

	public void setStrProvinceCodeMerchant(String strProvinceCodeMerchant) {
		this.strProvinceCodeMerchant = strProvinceCodeMerchant;
	}

	public String getStrProvinceNameMerchant() {
		return strProvinceNameMerchant;
	}

	public void setStrProvinceNameMerchant(String strProvinceNameMerchant) {
		this.strProvinceNameMerchant = strProvinceNameMerchant;
	}

	public String getStrCityCodeMerchant() {
		return strCityCodeMerchant;
	}

	public void setStrCityCodeMerchant(String strCityCodeMerchant) {
		this.strCityCodeMerchant = strCityCodeMerchant;
	}

	public String getStrCityNameMerchant() {
		return strCityNameMerchant;
	}

	public void setStrCityNameMerchant(String strCityNameMerchant) {
		this.strCityNameMerchant = strCityNameMerchant;
	}

	public Long getlSalesWeight() {
		return lSalesWeight;
	}

	public void setlSalesWeight(Long lSalesWeight) {
		this.lSalesWeight = lSalesWeight;
	}

	public Long getlNewWeight() {
		return lNewWeight;
	}

	public void setlNewWeight(Long lNewWeight) {
		this.lNewWeight = lNewWeight;
	}

	public Long getlActivityWeight() {
		return lActivityWeight;
	}

	public void setlActivityWeight(Long lActivityWeight) {
		this.lActivityWeight = lActivityWeight;
	}

	public Long getlDistanceWeight() {
		return lDistanceWeight;
	}

	public void setlDistanceWeight(Long lDistanceWeight) {
		this.lDistanceWeight = lDistanceWeight;
	}

	public Long getlExpenseWeight() {
		return lExpenseWeight;
	}

	public void setlExpenseWeight(Long lExpenseWeight) {
		this.lExpenseWeight = lExpenseWeight;
	}

	public Long getlWeightCount() {
		return lWeightCount;
	}

	public void setlWeightCount(Long lWeightCount) {
		this.lWeightCount = lWeightCount;
	}

}
