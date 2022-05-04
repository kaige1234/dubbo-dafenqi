package com.xl.es.data.doc;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Result implements Serializable{
	private String msg;//结果信息
	private Integer code;//代码
	private String error;//异常信息
	private Boolean success=true;//是否成功
	private Map<String,Object> data=new HashMap<String,Object>();
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Result putData(String name,Object obj){
		data.put(name, obj);
		return this;
	}
	
	public Result(){
	}
	
	public Result(Integer code,String msg,Boolean success,String error){
		this.code=code;
		this.msg=msg;
		this.success=success;
		this.error=error;
	}
	
	public static Result getOK(){
		return new Result(0,"ok",true,null);
	}
	
	public static Result getError(){
		return new Result(-1,"error",false,null);
	}
	
	public static Result getError(String msg){
		return new Result(-1,msg,false,null);
	}
	
	public static Result getError(String msg,String error){
		return new Result(-1,msg,false,error);
	}
	
	public static Result getOK(String msg){
		return new Result(0,msg,true,null);
	}
	public static Result getOk(String name,Object value){
		Result rs=Result.getOK();
		rs.putData(name, value);
		return rs;
	}
}
