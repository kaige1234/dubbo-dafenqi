package com.xl.commons.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import com.xl.commons.util.CmmonsDateConverter;
import com.xl.commons.util.EhCacheUtil;
import sun.misc.BASE64Encoder;

public class BaseController {
	private static final Integer defaultPageSize=20;//默认每页记录数
	protected EhCacheUtil cacheUtil=EhCacheUtil.getInstacne();
	static{
		ConvertUtils.register(new CmmonsDateConverter(), Date.class);
	}
	/**
	 * 获得每页记录条数
	 * @param request
	 * @return
	 */
	protected Integer getPageSize(HttpServletRequest request)
	{
		String pageSize=request.getParameter("query_pageSize");
		if(pageSize!=null&&!pageSize.trim().equals(""))
		{
			return Integer.parseInt(pageSize);
		}
		pageSize=request.getParameter("pageSize");
		if(pageSize!=null&&!pageSize.trim().equals(""))
		{
			return Integer.parseInt(pageSize);
		}
		pageSize=request.getParameter("limit");
		if(pageSize!=null&&!pageSize.trim().equals(""))
		{
			return Integer.parseInt(pageSize);
		}
		return defaultPageSize;
	}
	/**
	 * 获得当前页
	 * @param request
	 * @return
	 */
	protected Integer getPageNumber(HttpServletRequest request)
	{
		String pageNum=request.getParameter("query_pageNum");
		if(pageNum!=null&&!pageNum.trim().equals(""))
		{
			return Integer.parseInt(pageNum);
		}
		pageNum=request.getParameter("pageNumber");
		if(pageNum!=null&&!pageNum.trim().equals(""))
		{
			return Integer.parseInt(pageNum);
		}
		pageNum=request.getParameter("pageNum");
		if(pageNum!=null&&!pageNum.trim().equals(""))
		{
			return Integer.parseInt(pageNum);
		}
		pageNum=request.getParameter("curr");
		if(pageNum!=null&&!pageNum.trim().equals(""))
		{
			return Integer.parseInt(pageNum);
		}
		return 1;
	}
	/**
	 * 根据名称，获得String参数
	 * @param request
	 * @param name
	 * @param defaultValue 默认值
	 * @return
	 */
	protected String getParam(HttpServletRequest request,String name,String defaultValue)
	{
		String value=request.getParameter(name);
		if(value!=null)
		{
			if(request.getMethod().equals("GET"))
			{
				try {
					return new String(value.getBytes("iso-8859-1"),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		return defaultValue;
	}
	/**
	 * 根据名称，获得Integer参数
	 * @param request
	 * @param name
	 * @param defaultValue 默认值
	 * @return
	 */
	protected Integer getIntParam(HttpServletRequest request,String name,Integer defaultValue)
	{
		String value=this.getParam(request, name);
		if(value!=null)
		{
			return Integer.parseInt(value);
		}
		return defaultValue;
	}
	/**
	 * 根据名称，获得Long参数
	 * @param request
	 * @param name
	 * @param defaultValue 默认值
	 * @return
	 */
	protected Long getLongParam(HttpServletRequest request,String name,Long defaultValue)
	{
		String value=this.getParam(request, name);
		if(value!=null)
		{
			return Long.parseLong(value);
		}
		return defaultValue;
	}
	
	/**
	 * 根据名称，获得Double参数
	 * @param request
	 * @param name
	 * @param defaultValue 默认值
	 * @return
	 */
	protected Double getDoubleParam(HttpServletRequest request,String name,Double defaultValue)
	{
		String value=this.getParam(request, name);
		if(value!=null)
		{
			return Double.parseDouble(value);
		}
		return defaultValue;
	}
	
	/**
	 * 根据名称，获得String参数
	 * @param request
	 * @param name
	 * @return
	 */
	protected String getParam(HttpServletRequest request,String name)
	{
		String value=request.getParameter(name);
		if(value!=null)
		{
			if(request.getMethod().equals("GET"))
			{
				try {
					value = new String(value.getBytes("iso-8859-1"),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}
	/**
	 * 根据名称，获得Integer参数
	 * @param request
	 * @param name
	 * @return
	 */
	protected Integer getIntParam(HttpServletRequest request,String name)
	{
		String value=this.getParam(request, name);
		if(value!=null)
		{
			return Integer.parseInt(value);
		}
		return null;
	}
	/**
	 * 根据名称，获得Long参数
	 * @param request
	 * @param name
	 * @return
	 */
	protected Long getLongParam(HttpServletRequest request,String name)
	{
		String value=this.getParam(request, name);
		if(value!=null)
		{
			return Long.parseLong(value);
		}
		return null;
	}
	
	/**
	 * 根据名称，获得Double参数
	 * @param request
	 * @param name
	 * @return
	 */
	protected Double getDoubleParam(HttpServletRequest request,String name)
	{
		String value=this.getParam(request, name);
		if(value!=null)
		{
			return Double.parseDouble(value);
		}
		return null;
	}
	
	/**
	 * 获得查询条件
	 * @param request
	 * @param queryClass
	 * @return
	 */
	protected <T> T getQuery(HttpServletRequest request,Class<T> queryClass)
	{
		Object target=null;
		try {
			target = queryClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		Enumeration paramets = request.getParameterNames();
		boolean havePageSize=false;
		boolean havePageNum=false;
		while (paramets.hasMoreElements()) {
			String keys = (String) paramets.nextElement();
			String values="";
			try {
				values = request.getParameter(keys);
				if(request.getMethod().equals("GET"))
				{
					if(values!=null)
					{
						values = new String(values.getBytes("iso-8859-1"),"UTF-8");
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if (keys.startsWith("query.")||keys.startsWith("query_")) {
				String newKey=keys.substring("query.".length(),keys.length());
				try {
						if(newKey.equals("pageSize"))
						{
							havePageSize=true;
						}
						if(newKey.equals("pageNum"))
						{
							havePageNum=true;
						}
						if(values!=null&&!values.trim().equals(""))
						{
							if(target instanceof HashMap){
								((Map)target).put(newKey, values);
							}else{
								BeanUtils.setProperty(target, newKey, values);
							}
						}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		if (!havePageSize) {
			try {
				if(target instanceof HashMap){
					((Map)target).put("pageSize",defaultPageSize );
				}else{
					BeanUtils.setProperty(target, "pageSize",defaultPageSize );
				}
				
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		if (!havePageNum) {
			try {
				if(target instanceof HashMap){
					((Map)target).put("pageNum",1 );
				}else{
					BeanUtils.setProperty(target, "pageNum", 1);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return (T) target;
	}
	
	
	/**
	 * 获得查询条件查询
	 * @param request
	 * @return
	 */
	protected Map getQueryParams(HttpServletRequest request)
	{
		Map parameter = new HashMap();
		Enumeration paramets = request.getParameterNames();
		while (paramets.hasMoreElements()) {
			String keys = (String) paramets.nextElement();
			String values = request.getParameter(keys);
			if (keys.startsWith("query.")||keys.startsWith("query_")) {
				if(values!=null&&!values.trim().equals(""))
				{
					parameter.put(keys, values);
				}
			}
		}
		parameter.put("query_pageSize", this.getPageSize(request));
		parameter.put("query_pageNum", this.getPageNumber(request));
		parameter.put("query_orderField", request.getParameter("query_orderField"));
		parameter.put("query_orderDirection", request.getParameter("query_orderDirection"));
		return parameter;
	}
	
	protected static Hashtable parseQueryString(String s)
	  {
	    String[] valArray = null;
	    if (s == null) {
	      throw new IllegalArgumentException("queryString must not null");
	    }
	    Hashtable ht = new Hashtable();
	    StringBuffer sb = new StringBuffer();
	    StringTokenizer st = new StringTokenizer(s, "&");
	    while (st.hasMoreTokens()) {
	      String pair = st.nextToken();
	      if (pair.trim().length() != 0)
	      {
	        int pos = pair.indexOf('=');
	        if (pos == -1) {
	          throw new IllegalArgumentException("cannot parse queryString:" + s);
	        }
	        String key = parseName(pair.substring(0, pos), sb);
	        String val = parseName(pair.substring(pos + 1, pair.length()), sb);
	        if (ht.containsKey(key)) {
	          String[] oldVals = (String[])ht.get(key);
	          valArray = new String[oldVals.length + 1];
	          for (int i = 0; i < oldVals.length; i++)
	            valArray[i] = oldVals[i];
	          valArray[oldVals.length] = val;
	        } else {
	          valArray = new String[1];
	          valArray[0] = val;
	        }
	        ht.put(key, valArray);
	      }
	    }
	    return fixValueArray2SingleStringObject(ht);
	  }

	  private static Hashtable fixValueArray2SingleStringObject(Hashtable ht)
	  {
	    Hashtable result = new Hashtable();
	    for (Iterator it = ht.entrySet().iterator(); it.hasNext(); ) {
	      Map.Entry entry = (Map.Entry)it.next();
	      String[] valueArray = (String[])entry.getValue();
	      if (valueArray == null)
	        result.put(entry.getKey(), valueArray);
	      else
	        result.put(entry.getKey(), valueArray.length == 1 ? valueArray[0] : valueArray);
	    }
	    return result;
	  }

	  private static String parseName(String s, StringBuffer sb) {
	    sb.setLength(0);
	    for (int i = 0; i < s.length(); i++) {
	      char c = s.charAt(i);
	      switch (c) {
	      case '+':
	        sb.append(' ');
	        break;
	      case '%':
	        try {
	          sb.append((char)Integer.parseInt(s.substring(i + 1, i + 3), 16));

	          i += 2;
	        }
	        catch (NumberFormatException e) {
	          throw new IllegalArgumentException();
	        } catch (StringIndexOutOfBoundsException e) {
	          String rest = s.substring(i);
	          sb.append(rest);
	          if (rest.length() == 2) {
	            i++;
	          }
	        }

	      default:
	        sb.append(c);
	      }
	    }

	    return sb.toString();
	  }
	  
	  
	  /**
	   * 获得当前class的根目录
	   */
	  public String getClassRootPath()
	  {
		  String path = this.getClass().getClassLoader().getResource("").getPath();
		  if(path.toLowerCase().indexOf("/WEB-INF/classes/".toLowerCase())==-1)
		  {
			  path=this.getClass().getResource("").getPath();
			  if(path.toLowerCase().indexOf("/WEB-INF/classes/".toLowerCase())==-1)
			  {
				  return "";
			  }
		  }
		  path=path.substring(0,path.toLowerCase().indexOf("/WEB-INF/classes/".toLowerCase())+"/WEB-INF/classes/".length());
		  return path.replaceAll("%20", " ");
	  }
	  
	  /**
	   * 对象属性拷贝
	   * @param dest
	   * @param orig
	   * @param copyNull 是否拷贝空属性，当为false时，不拷贝
	   * @throws IllegalAccessException
	   * @throws InvocationTargetException
	   */
	  public void copyProperties(Object dest, Object orig, boolean copyNull)throws IllegalAccessException, InvocationTargetException {
		  com.xl.commons.util.BeanUtils.copyProperties(dest, orig,copyNull);
		}
	  
	  
	  /**
	   * 添加重复提交的代码
	   * @param request
	   */
	  protected void addRepeatSubmitCode(HttpServletRequest request)
	  {
		  String code=UUID.randomUUID().toString();
		  request.getSession().setAttribute("_repeat_submit_code_", code);
	  }
	  
	  /**
	   * 检查当前提交是否为重复提交
	   * @param request
	   * @return
	   */
	  protected boolean checkRepeatSubmit(HttpServletRequest request)
	  {
		  String session_repeat_submit_code_=(String)request.getSession().getAttribute("_repeat_submit_code_");
		  String request_repeat_submit_code=(String)request.getParameter("_repeat_submit_code_");
		  
		  if(request_repeat_submit_code!=null&&!request_repeat_submit_code.equals(""))
		  {
			  if(session_repeat_submit_code_==null){session_repeat_submit_code_="";}
			  if(!session_repeat_submit_code_.equals(request_repeat_submit_code))
			  {
				  return true;
			  }
		  }
		  request.getSession().removeAttribute("_repeat_submit_code_");
		  return false;
	  }
	  
	  
	  public void resetDoloadFileName(HttpServletResponse response,String fileName)throws Exception
	  {
	    	response.reset();  
	    	response.setContentType("application/octet-stream; charset=utf-8");  
	        response.setHeader("Content-Disposition","attachment;filename="+new String(fileName.getBytes("gbk"),"iso-8859-1"));
	  }
	  
	  
	  protected static String getStackTrace(Throwable t)
		{
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			try{
				t.printStackTrace(pw);
				return sw.toString();
			}
			finally{
				if (sw != null) {
	                try {
	                    sw.close();
	                } catch (IOException e1) {
	                    e1.printStackTrace();
	                }
	            }
	            if (pw != null) {
	                pw.close();
	            }
			}
		}
	/**
	 * 添加md5加密方法
	 * @param str
	 * @return String
	 * @author yj
	 */
	public String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		//确定计算方法
		MessageDigest md5= MessageDigest.getInstance("MD5");
		BASE64Encoder base64en = new BASE64Encoder();
		//加密后的字符串
		String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
		return newstr;
	}
}
