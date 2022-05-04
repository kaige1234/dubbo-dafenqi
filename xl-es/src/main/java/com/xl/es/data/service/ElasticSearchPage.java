package com.xl.es.data.service;

import java.util.List;
/**
 * 分页对象
 * @author liufeng
 *
 * @param <T>
 */
public class ElasticSearchPage<T> {
	 
    private String scrollId;
 
    private long total;
 
    private int pageSize=10;
 
    private int pageNum;
 
    private T param;
 
    private List<T> retList;
 
    private List<String> scrollIds;
    private long totalPage;
    public ElasticSearchPage(){
    	
    }
    public ElasticSearchPage(int pageSize){
    	this.pageSize=pageSize;
    }
    public ElasticSearchPage(int pageSize,int pageNum){
    	this.pageNum=pageNum;
    	this.pageSize=pageSize;
    }
    public List<String> getScrollIds() {
        return scrollIds;
    }
 
    public void setScrollIds(List<String>scrollIds) {
        this.scrollIds =scrollIds;
    }
 
    public List<T> getRetList() {
        return retList;
    }
 
    public void setRetList(List<T> retList) {
        this.retList =retList;
    }
 
    public String getScrollId() {
        return scrollId;
    }
 
    public void setScrollId(String scrollId) {
        this.scrollId =scrollId;
    }
 
    public long getTotal() {
        return total;
    }
 
    public void setTotal(long total){
        this.total =total;
        this.totalPage=this.getTotalPage();
    }
 
    public int getPageSize() {
         return pageSize;
    }
 
    public void setPageSize(int pageSize){
        this.pageSize =pageSize;
    }
 
    public int getPageNum() {
        if(pageNum <=0){
            return 0;
        }else{
            return pageNum -= 1;
        }
    }
 
    public void setPageNum(int pageNum){
        this.pageNum =pageNum;
    }
 
    public T getParam() {
        return param;
    }
 
    public void setParam(T param) {
        this.param =param;
    }
 
    public long getTotalPage(){
    	if (total == 0 || pageSize == 0)
			return 0;
		if (total % pageSize == 0)
			return total / pageSize;
		else
			return total / pageSize + 1;
    }
    
    
    public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}
	@Override
    public String toString() {
        return"Page{" +
                "scrollId='"+ scrollId + '\''+
                ",total=" + total +
                ",pageSize=" + pageSize +
                ",pageNum=" + pageNum +
                ",param=" + param +
                ",retList=" + retList +
                ",scrollIds=" + scrollIds +
                '}';
    }
}