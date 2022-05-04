package com.xl.commons.util;

import java.util.ArrayList;
import java.util.List;

public class DataChangeHelper {

	private String[] oldData;// 原数据
	private String[] newData;// 新数据

	public DataChangeHelper(String[] oldData, String[] newData) {
		this.oldData = oldData;
		this.newData = newData;
	}

	/**
	 * 获取新增数据
	 * 
	 * @return
	 */
	public List<String> getAddData() {
		List<String> data = new ArrayList<String>();
		// 如果原数据与新数据都为空，则直接返回空数据集
		if (oldData.length == 0 && newData.length == 0) {
			return data;
		}
		// 如果原数据为空，新数据不为空，则新增数据为新数据
		if (oldData.length == 0 && newData.length > 0) {
			for (String id : newData) {
				if (!id.equals("")) {
					data.add(id);
				}
			}
			return data;
		}

		// 新数据不在老数据中存在时，则说明为需要新增
		for (String newItem : newData) {
			boolean isHave = false;
			for (String oldItem : oldData) {
				if (newItem.equals(oldItem)) {
					isHave = true;
					break;
				}
			}
			if (!isHave) {
				if (!newItem.equals("")) {
					data.add(newItem);
				}
			}
		}
		return data;
	}

	/**
	 * 获得删除数据
	 * 
	 * @return
	 */
	public List<String> getDeleteData() {
		List<String> data = new ArrayList<String>();
		// 如果原数据与新数据都为空，则直接返回空数据集
		if (oldData.length == 0 && newData.length == 0) {
			return data;
		}
		// 如果原数据不为空，新数据为空，则直接将原数据全部进行删除
		if (newData.length == 0 && oldData.length > 0) {
			for (String id : oldData) {
				if (!id.equals("")) {
					data.add(id);
				}
			}
			return data;
		}

		// 老数据不在新数据中存在时，则说明为需要删除
		for (String oldItem : oldData) {
			boolean isHave = false;
			for (String newItem : newData) {
				if (oldItem.equals(newItem)) {
					isHave = true;
					break;
				}
			}
			if (!isHave) {
				if (!oldItem.equals("")) {
					data.add(oldItem);
				}
			}
		}
		return data;
	}
}
