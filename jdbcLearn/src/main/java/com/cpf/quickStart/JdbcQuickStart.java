package com.cpf.quickStart;


import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author CPF 创建于： 2021/8/9 17:46
 * @version 1.0
 * JDBC操作数据库快速入门:
 */
public class JdbcQuickStart{
	public static void main(String[] args) throws SQLException{
		/*最开始的一步是导入jdbc相关的驱动*/
		//1.注册驱动
		Driver driver = new Driver();

		//2.得到连接
		/*对下面这句话的解读：
		(1)jdbc:mysql:// 是一种格式（协议），表示以jdbc方式连接mysql数据库
		(2)localhost:3306/ 表示本机3306端口，这个可人为调整，远程需写ip地址
		(3)school表示mysql内某个具体的数据库
		(4)mysql的连接，本质上就是socket连接
		 */
		String url = "jdbc:mysql://localhost:3306/school";

		//将用户、密码放入Properties对象中，将来可以存入.properties文件
		Properties properties = new Properties();
		properties.setProperty("user","root");//user是固定的
		properties.setProperty("password","cpf");//password也是固定的

		//Connection connect(String url, java.util.Properties info)
		Connection connect = driver.connect(url, properties);//得到网络接口连接

		//3.执行sql
		String sql = "INSERT INTO student VALUES(10,'刘德华',1,3,'swim',47)";
		//String sql = "DELETE FROM student WHERE `name` = '刘德华'";
		//statement用于执行静态SQL语句并返回其生成结果
		Statement statement = connect.createStatement();
		int rows = statement.executeUpdate(sql);//返回受影响的行数
		System.out.println(rows > 0 ? "插入成功" : "插入失败");


		//4.关闭连接资源
		statement.close();
		connect.close();
	}
}
