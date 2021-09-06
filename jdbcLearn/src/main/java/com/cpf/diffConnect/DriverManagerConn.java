package com.cpf.diffConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author CPF 创建于： 2021/8/9 22:31
 * @version 1.0
 * 使用DriverManager的好处：
 * 1.更丰富的连接方法
 * 2.可以不用显示地注册
 * 3.彻底舍弃第三方库中的Driver类，依赖性低
 *
 * 前三种方式中，这种方式是最简洁的，因为DriverManager是java.sql包下的，是自带的类
 */
public class DriverManagerConn{
	public static void main(String[] args) throws Exception{
		String path = "com.mysql.jdbc.Driver";//将来这个字符串可以放入配置文件
		//通过反射获得Driver类
		Class driver = Class.forName(path);
		/*
		按理说，应该要这样加载一下Driver类，因为其中包含了静态代码块，用于注册驱动，
			 static {
		        try {
		            DriverManager.registerDriver(new Driver());
		        } catch (SQLException var1) {
		            throw new RuntimeException("Can't register driver!");
		        }
	         }
		但事实上，没有显式地加载类，也可以完成注册，这是为什么呢？
		1.mysql驱动包5.1.6以上，包含有META-INF/service/java.sql.Driver，里面包含了自动注册的驱动
		2.从JDK1.5以后使用的是jdbc4,不再需要显示地注册驱动，而是自动调用驱动包下的
			META-INF/service/java.sql.Driver文本中的类名称去注册，
		换句话说，系统自动注册了相关的类
		 */

		String url = "jdbc:mysql://localhost:3306/school";

		//将用户、密码放入Properties对象中，将来可以存入.properties文件
		Properties properties = new Properties();
		properties.setProperty("user","root");//user是固定的
		properties.setProperty("password","cpf");//password也是固定的

		Connection connection = DriverManager.getConnection(url, properties);

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
}
