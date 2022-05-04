package com.xl.sso.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
/**
 * 校验票据和注销子系统时使用httpclient请求
 * @author wangchengbin
 * @date  2017年11月17日-下午3:19:02
 */
public class HttpUtil {
	
	private static final String charset="UTF-8";
	
	/**
	 * 向目标url发送post请求
	 * @param url
	 * @param params
	 * @return
	 */
	public static boolean post(String url, Map<String, String> params) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		// 参数处理
		if (params != null && !params.isEmpty()) {
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			
			Iterator<Entry<String, String>> it = params.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			
			httpPost.setEntity(new UrlEncodedFormEntity(list, Consts.UTF_8));
		}
		// 执行请求
		try {
			CloseableHttpResponse response = httpclient.execute(httpPost);
			response.getStatusLine().getStatusCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 *post请求方法
	 * 
	 * @param url		请求地址
	 * @param headers	头部参数
	 * @param params    请求参数
	 * @return 链接响应内容
	 */
	@SuppressWarnings("all")
	public static String doPost(String url,Map<String,String> headers, Map<String, String> params){
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = new DefaultHttpClient();
			httpPost = new HttpPost(url);
			//设置头部参数
			if (headers!=null) {
				for (Map.Entry<String, String> header : headers.entrySet()) {
					httpPost.addHeader(header.getKey(), header.getValue());
				}
			}
			// 参数处理
			if (params != null && !params.isEmpty()) {
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> param : params.entrySet()) {
					list.add(new BasicNameValuePair(param.getKey(), param.getValue()));
				}
				
				httpPost.setEntity(new UrlEncodedFormEntity(list, Consts.UTF_8));
			}
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			httpPost.releaseConnection();
			httpClient.getConnectionManager().closeIdleConnections(0,TimeUnit.MILLISECONDS);
		}
		return result;
	}
}