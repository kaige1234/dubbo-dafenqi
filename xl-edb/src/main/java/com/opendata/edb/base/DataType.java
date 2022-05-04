package com.opendata.edb.base;

/**
 * 定义excel文件中的单元格有哪些数据类型
 * @author 刘丰
 *
 */
public class DataType
{

	public static final String Date = "date";// 日期类型
	public static final String String = "string";// 字符串类型
	public static final String Number = "number";// 数字类型
	public static final String defaultFormatPatten="yyyy-MM-dd";//日期类型默认格式化
	public static final String FormDataGridModel="form";//单元格数据模式 表单形
	public static final String ListDataGridModel="list";//单元格数据模式 列表形
	public static final String  FixListDataGridModel="fixlist";//固定高度的数据模式，列表形  需要指定其总行数 sumRow=5 则此列表总共有5行数据
	public static final String ListAndFormDataGridModel="listAndForm";//列表与表单组合
	
	
}
