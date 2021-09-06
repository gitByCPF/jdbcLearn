package com.cpf.diffConnect;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author CPF 创建于： 2021/8/9 21:39
 * @version 1.0
 * 1.最基本的创建com.mysql.jdbc.Driver类来获得数据库连接
 *      缺点：显而易见，这个Driver是第三方类，依赖性太高，
 * 2.通过反射获得这个驱动，能一定程度解耦合
 *      将来还可以放入Spring中由容器来创建驱动对象
 */
public class ReflectConn{
	public static void main(String[] args) throws Exception{
		String path = "com.mysql.jdbc.Driver";//将来这个字符串可以放入配置文件
		//通过反射获得Driver类
		Class driver = Class.forName(path);
		//新建一个对象
		Object obj = driver.newInstance();

		//获得方法connect的对象，就不必强制类型转换了
		//Method connect = driver.getDeclaredMethod("connect");
		//Class superclass = driver.getSuperclass();//connect方法继承自其父类

		Method connect = driver.getMethod("connect", String.class, Properties.class);

		String url = "jdbc:mysql://localhost:3306/school";

		//将用户、密码放入Properties对象中，将来可以存入.properties文件
		Properties properties = new Properties();
		properties.setProperty("user","root");//user是固定的
		properties.setProperty("password","cpf");//password也是固定的

		//Connection connect(String url, java.util.Properties info)
		Connection connection = (Connection) connect.invoke(obj, url, properties);

		//3.执行sql
		String sql = "INSERT INTO student VALUES(10,'刘德华',1,3,'swim',47)";
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
