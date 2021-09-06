package com.cpf.diffConnect;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author CPF 创建于： 2021/8/9 23:07
 * @version 1.0
 * 前面四种，连接数据库、登录数据库的语句都被写入代码，属于硬编码，灵活性极差，
 * 完全可以把这些写入.properties配置文件中，非常灵活
 *
 * 推荐使用第五种方式，配置文件+DriverManager 虽然有自动驱动，但还是建议反射获取类，手动注册
 */
public class PropertiesConn{
	public static void main(String[] args) throws Exception{
		Properties properties = new Properties();
		//放入resources内的资源，有特殊的方式来获取文件流
		properties.load(
				PropertiesConn.class.getResourceAsStream("/mysql.properties"));

		//通过配置文件获得相应信息
		String url = properties.getProperty("url");
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");
		//连接数据库
		Class.forName("com.mysql.jdbc.Driver");//显示地注册会更稳妥
		Connection connection = DriverManager.getConnection(url, user, password);

		//3.执行sql
		String sql = "INSERT INTO student VALUES(10,'梅兰芳',1,3,'swim',47)";
		//String sql = "DELETE FROM student WHERE `name` = '刘德华'";
		//statement用于执行静态SQL语句并返回其生成结果
		Statement statement = connection.createStatement();
		int rows = statement.executeUpdate(sql);//返回受影响的行数
		System.out.println(rows > 0 ? "插入成功" : "插入失败");


		//4.关闭连接资源
		statement.close();
		connection.close();



	}


	public void setProperties() throws IOException{
		File file = new File("src/main/resources/mysql.properties");//配置文件

		Properties properties = new Properties();
		properties.setProperty("url", "jdbc:mysql://localhost:3306/school");
		properties.setProperty("user", "root");
		properties.setProperty("password", "cpf");

		properties.store(new FileOutputStream(file),
				"this is mysql_connection information");
		System.out.println("配置文件完毕");
	}
}
