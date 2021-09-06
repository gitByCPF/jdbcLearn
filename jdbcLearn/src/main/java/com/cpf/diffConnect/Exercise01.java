package com.cpf.diffConnect;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author CPF 创建于： 2021/8/10 11:55
 * @version 1.0
 */
public class Exercise01{
	public static void main(String[] args) throws Exception{
		Properties properties = new Properties();
		//File file = new File("E:/IDEA_2020_2_4/IdeaProjects/jdbcLearn/jdbcLearn/src/main/resources/mysql.properties");
		properties.load(Exercise01.class.getResourceAsStream("/mysql.properties"));

		String url = properties.getProperty("url");
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");

		//sql语句
		String createTable = "CREATE TABLE news(id INT,content TEXT)";
		String insertValue = "INSERT INTO news VALUES" +
				"(1,'专访苏炳添外籍教练：9秒83不是极限')," +
				"(2,'扬州疫情追踪：348例确诊病例超一半60岁以上，全省支援')," +
				"(3,'靠泊舟山的“弘进”轮 16名船员核酸检测结果呈阳性')," +
				"(4,'郑州35例无症状感染者转确诊 累计报告本土确诊116例')," +
				"(5,'美在阿富汗边撤军边空袭，阿塔发出警告')";
		//数据库连接
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection(url, user, password);

		//获得statement
		Statement statement = connection.createStatement();
		int rows1 = statement.executeUpdate(createTable);

		System.out.println(rows1 == 0 ? "建表成功" : "建表失败");
		int rows2 = statement.executeUpdate(insertValue);
		System.out.println(rows2 > 0 ? "插入成功" : "插入失败");

		//关闭连接
		statement.close();
		connection.close();


	}
}
