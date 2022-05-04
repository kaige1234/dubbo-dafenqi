package com.xl.commons.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 重写HttpServletRequestWrapper
 */
public class ScriptRequestWrapper extends HttpServletRequestWrapper {

    public ScriptRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public static String format(String fmtStr){
        if(org.apache.commons.lang.StringUtils.isEmpty(fmtStr)){
        	return fmtStr;	
        }
        return fmtStr.replaceAll("<script>", "&lt;script&gt;");
        
    }
    
    
    
    @Override
    public String getParameter(String name) {
        return  format(super.getParameter(name));
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] pv=super.getParameterValues(name);
        if(pv!=null&&pv.length>0){
            for(int i=0;i<pv.length;i++){
                pv[i]=format(pv[i]);
            }
        }
        return pv;
    }


}