package com.cpf.sqlInject;

import com.cpf.utils.JdbcUtils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author CPF 创建于： 2021/8/12 3:13
 * @version 1.0
 * 使用PreparedStatement进行DML操作；
 * DML操作：插入，修改，删除
 */
public class PreparedStatementDML{
	public static void main(String[] args) throws Exception{
		//获得配置文件流
		InputStream resourceAsStream =
				PreparedStatementDML.class.getResourceAsStream("/mysql.properties");
		//获得驱动全类名
		String driver = "com.mysql.jdbc.Driver";

		//实用工具类简化代码
		Connection connection = JdbcUtils.getConnection(driver, resourceAsStream);

		//sql语句：修改user码为123
		String sql = "update `user` set pwd = ?";
		String query = "select * from `user`";

		//根据连接获得预处理对象
		PreparedStatement ps = connection.prepareStatement(sql);

		PreparedStatement psQuery = connection.prepareStatement(query);
		//给预处理的sql语句补充完整
		ps.setInt(1, 123);

		//调用预处理对象的适当方法来进行sql操作
		int rows = ps.executeUpdate();
		System.out.println(rows > 0 ? "修改成功" : "修改失败");

		ResultSet resultSet = psQuery.executeQuery();
		System.out.println("名字\t密码");
		while(resultSet.next()){
			String name = resultSet.getString(1);
			int pwd = resultSet.getInt(2);
			if(name.length() >= 4)
				System.out.println(name + "\t" + pwd);
			else
				System.out.println(name + "\t\t" + pwd);
		}

		//关闭预处理对象、关闭数据库连接
		ps.close();
		psQuery.close();
		connection.close();

	}
}
