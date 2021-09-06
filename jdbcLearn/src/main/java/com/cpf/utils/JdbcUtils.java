package com.cpf.utils;

import com.cpf.sqlInject.PreparedStatementDML;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author CPF 创建于： 2021/8/12 16:09
 * @version 1.0
 * 数据库操作的 连接、关闭操作具有很多相似性，因此封装成工具类，减少代码冗余；
 */
public class JdbcUtils{
	//首先确定数据库参数
	private static String url;
	private static String user;
	private static String password;


	public static Connection getConnection() throws Exception{
		//获得配置文件流
		InputStream rs = Class.class.getResourceAsStream("/mysql.properties");
		//获得驱动全类名
		String driver = "com.mysql.jdbc.Driver";
		//手动注册
		Class.forName(driver);
		//加载mysql配置文件
		Properties properties = new Properties();
		properties.load(rs);
		//获得连接数据库相应的参数
		url = properties.getProperty("url");
		user = properties.getProperty("user");
		password = properties.getProperty("password");

		return DriverManager.getConnection(url, user, password);

	}

	//要获得连接，一个是数据库相关参数不可缺少，一个是驱动类型
	public static Connection getConnection(String driver, InputStream inStream) throws Exception{
		//手动注册
		Class.forName(driver);
		//加载mysql配置文件
		Properties properties = new Properties();
		properties.load(PreparedStatementDML.class.
				getResourceAsStream("/mysql.properties"));
		//获得连接数据库相应的参数
		url = properties.getProperty("url");
		user = properties.getProperty("user");
		password = properties.getProperty("password");

		return DriverManager.getConnection(url, user, password);
	}

	public static Connection getConnection
			(String driver, String url, String user, String password) throws Exception{
		//手动注册
		Class.forName(driver);

		//获得连接
		return DriverManager.getConnection(url, user, password);//就近原则
	}



}
