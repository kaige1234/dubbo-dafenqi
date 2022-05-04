package com.opendata.edb.reader;


public class DefaultReaderRow implements ReaderRow {

	public Object readListRow(String sheetName,int listIndex,Object obj) {
		return obj;
	}

	public Object readFormRow(String sheetName,Object obj) {
		return obj;
	}

}
