package com.cpf.sqlInject;

import com.cpf.resultSet.ResultSetIntro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author CPF 创建于： 2021/8/12 0:35
 * @version 1.0
 * Statement作为最开始的类，存在 SQL注入问题，因此不能使用
 */
public class StatementUse{
	public static void main(String[] args) throws Exception{
		Properties properties = new Properties();
		properties.load
				(ResultSetIntro.class.getResourceAsStream("/mysql.properties"));

		String url = properties.getProperty("url");
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");

		Class.forName("com.mysql.jdbc.Driver");//注册
		Connection connection = DriverManager.getConnection(url, user, password);

		//sql注入语句
		String sql = "select * from `user` " +
				"WHERE `name` = '1' OR ' and pwd = 'OR '1' = '1' ";

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		System.out.println("姓名\t密码");
		while(resultSet.next()){
			String name = resultSet.getString(1);
			int pwd = resultSet.getInt(2);
			if(name.length() >= 4)
				System.out.println(name + "\t" + pwd);
			else
				System.out.println(name + "\t\t" + pwd);
		}

		//关闭
		resultSet.close();//结果集要关闭
		statement.close();//数据库操作连接要关闭
		connection.close();//数据库连接关闭
	}
}
