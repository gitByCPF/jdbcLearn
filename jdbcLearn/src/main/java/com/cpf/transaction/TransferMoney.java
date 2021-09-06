package com.cpf.transaction;

import com.cpf.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author CPF 创建于： 2021/8/15 1:18
 * @version 1.0
 */
public class TransferMoney{
	//建表
	public void createTable() throws Exception{
		Connection connection = JdbcUtils.getConnection();
		String sql = "CREATE TABLE `account`(" +
						"id INT," +
						"`name` VARCHAR(20)," +
						"balance INT" +
					")";
		PreparedStatement ps = connection.prepareStatement(sql);
		int rows = ps.executeUpdate();
		System.out.println(rows == 0 ? "建表成功" : " 建表失败");

		ps.close();
		connection.close();
	}

	//开户
	public void openAccount() throws Exception{
		Connection connection = JdbcUtils.getConnection();
		String sql = "insert into account values" +
						"(1,'马云',1000)," +
						"(2,'马化腾',2000)";
		PreparedStatement ps = connection.prepareStatement(sql);
		int rows = ps.executeUpdate();
		System.out.println(rows > 0 ? "插入成功" : " 插入失败");

		ps.close();
		connection.close();
	}

	//转账
	public void transfer() throws Exception{
		Connection connection = JdbcUtils.getConnection();
		//开启事务---方式二，设置自动提交为false
		connection.setAutoCommit(false);
		//设置事务隔离级别
		connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

		String sql1 = "update `account` set balance = balance - 500 " +
						"where `name` = '马云'";
		String sql2 = "update `account` set balance = balance + 500 " +
				"where `name` = '马化腾'";
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		try{
			ps1 = connection.prepareStatement(sql1);
			ps2 = connection.prepareStatement(sql2);
			ps1.executeUpdate();
			ps2.executeUpdate();
			//如果上述代码无误，那么就说明转账执行成功，就直接提交
			//否则，一旦出现异常，下面的提交就不会被执行
			connection.commit();
			System.out.println("转账成功！");
		}catch(SQLException e){
			connection.rollback();
			System.out.println("转账失败!");
			System.out.println(e);
		}finally{
			ps1.close();
			ps2.close();
			connection.close();
		}

	}



}
