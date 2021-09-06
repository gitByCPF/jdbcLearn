package com.cpf.sqlInject;

import com.cpf.resultSet.ResultSetIntro;
import com.cpf.utils.JdbcUtils;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author CPF 创建于： 2021/8/12 0:41
 * @version 1.0
 */
public class PreparedStatementUse{
	public static void main(String[] args) throws Exception{
		InputStream resourceAsStream =
				ResultSetIntro.class.getResourceAsStream("/mysql.properties");
		String driver = "com.mysql.jdbc.Driver";

		Connection connection = JdbcUtils.getConnection(driver, resourceAsStream);

		//sql注入语句
		String sql = "select * from `user` WHERE `name` = ? and pwd = ?";

		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, "1' OR");
		preparedStatement.setString(2, "OR '1' = '1");

		ResultSet resultSet = preparedStatement.executeQuery();
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
		preparedStatement.close();//数据库操作连接要关闭
		connection.close();//数据库连接关闭
	}
}
