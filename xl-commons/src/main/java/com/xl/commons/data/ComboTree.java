package com.xl.commons.data;

import java.util.List;
import java.util.Map;

public class ComboTree implements java.io.Serializable {

	private String id;
	private String text;// 树节点名称
	private String iconCls;// 前面的小图标样式
	private String url;//地址
	private String code;//代码
	private Boolean checked = false;// 是否勾选状态
	private Map<String, Object> attributes;// 其他参数
	private List<ComboTree> children;// 子节点
	private String state = "open";// 是否展开(open,closed)

	public ComboTree(){
		
	}
	public ComboTree(String id,String text){
		this.id=id;
		this.text=text;
	}
	public ComboTree(String id,String text,String code,String url){
		this.id=id;
		this.text=text;
		this.code=code;
		this.url=url;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public List<ComboTree> getChildren() {
		return children;
	}

	public void setChildren(List<ComboTree> children) {
		this.children = children;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	public String getCode()
	{
		return code;
	}
	public void setCode(String code)
	{
		this.code = code;
	}
	
	
}
