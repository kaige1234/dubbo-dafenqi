package com.opendata.edb.reader;

public interface ReaderRow {

	/**
	 * 读取列表数据
	 * @param sheetName
	 * @param listIndex sheet中如有多个列表时的下标，从0开始
	 * @param obj
	 * @return
	 */
	public Object readListRow(String sheetName,int listIndex,Object obj);
	
	/**
	 * 读取form数据
	 * @param sheetName
	 * @param obj
	 * @return
	 */
	public Object readFormRow(String sheetName,Object obj);
}
