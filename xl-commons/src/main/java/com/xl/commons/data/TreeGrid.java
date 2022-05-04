package com.xl.commons.data;

import java.util.HashMap;
import java.util.Map;

public class TreeGrid implements java.io.Serializable
{
	private String id;
	private String text;
	private String parentId;
	private String parentText;
	private String code;
	private String src;
	private String note;
	private Map<String, String> attributes=new HashMap<String,String>();// 其他参数
	private String operations;// 其他参数
	private String state = "open";// 是否展开(open,closed)
	private String order;// 排序
	private boolean checked;//是否选中
	public static String state_open="open";
	public static String state_closed="closed";

	public boolean isChecked()
	{
		return checked;
	}

	public void setChecked(boolean checked)
	{
		this.checked = checked;
	}

	public String getOrder()
	{
		return order;
	}

	public void setOrder(String order)
	{
		this.order = order;
	}

	public String getOperations()
	{
		return operations;
	}

	public void setOperations(String operations)
	{
		this.operations = operations;
	}

	public Map<String, String> getAttributes()
	{
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes)
	{
		this.attributes = attributes;
	}

	public String getParentText()
	{
		return parentText;
	}

	public void setParentText(String parentText)
	{
		this.parentText = parentText;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getSrc()
	{
		return src;
	}

	public void setSrc(String src)
	{
		this.src = src;
	}

	public String getNote()
	{
		return note;
	}

	public void setNote(String note)
	{
		this.note = note;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String getParentId()
	{
		return parentId;
	}

	public void setParentId(String parentId)
	{
		this.parentId = parentId;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}
	
	public void putAttribute(String key,String value){
		this.attributes.put(key, value);
	}

}
