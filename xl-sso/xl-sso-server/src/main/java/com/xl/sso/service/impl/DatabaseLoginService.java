package com.xl.sso.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import com.xl.sso.service.LoginService;

/**
 * 登录服务
 * 
 * @author liufeng
 *
 */
public class DatabaseLoginService implements LoginService {

	@Resource
	private DataSource dataSource;

	private String querySql;
	private List<String> resultList;
	@Override
	public Map<String, String> login(String userName, String password) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			preparedStatement = conn.prepareStatement(querySql);
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, password);
			rs = preparedStatement.executeQuery();
			Map<String, String> result = null;
			while (rs.next()) {
				result = new HashMap<String, String>();
				for (String key : resultList) {
					result.put(key, rs.getString(key));
				}
			}
			return result;
		} catch (final Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public String getQuerySql() {
		return querySql;
	}

	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<String> getResultList() {
		return resultList;
	}

	public void setResultList(List<String> resultList) {
		this.resultList = resultList;
	}
}
