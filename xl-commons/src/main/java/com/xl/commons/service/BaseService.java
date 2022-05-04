package com.xl.commons.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public abstract  class BaseService<M extends BaseDao<T>,T>
{
	protected  M dao;
	protected abstract void setDao(M dao);
	 
	public int save(T entity){
        return dao.insert(entity);
    }
	
    public int delete(Long id){
        return dao.deleteByPrimaryKey(id);
    }
	
    public int update(T entity){
    	return dao.updateByPrimaryKey(entity);
    }
    
    @Transactional(readOnly=true)
    public  T getById(Long id){
    	return dao.selectByPrimaryKey(id);
    }
    
    protected  M getDao(){
    	return this.dao;
    }
}