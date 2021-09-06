package com.cpf.resultSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author CPF 创建于： 2021/8/11 23:42
 * @version 1.0
 * ResultSet对象保持一个光标指向其当前的数据行。 最初，光标位于第一行之前。
 * next方法将光标移动到下一行，并且由于在ResultSet对象中没有更多行时返回false ，
 * 因此可以在while循环中使用循环来遍历结果集。
 *
 * 所述ResultSet接口提供getter方法（ getBoolean ， getLong ，用于从当前行检索列值，等等）。
 * 可以使用列的索引号或列的名称来检索值。 一般来说，使用列索引将更有效率。 列从1编号。为了最大可移植性，
 * 每行中的结果集列应以从左到右的顺序读取，每列应只读一次
 */
public class ResultSetIntro{
	public static void main(String[] args) throws Exception{
		Properties properties = new Properties();
		properties.load
				(ResultSetIntro.class.getResourceAsStream("/mysql.properties"));

		String url = properties.getProperty("url");
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");

		Class.forName("com.mysql.jdbc.Driver");//注册
		Connection connection = DriverManager.getConnection(url, user, password);

		//sql语句
		String sql = "select * from student";
		/*
		+------+--------+-----------+----------+--------+------+
		| id   | name   | class_num | club_num | talent | age  |
		+------+--------+-----------+----------+--------+------+
		|    1 | jack   |         1 | 1        | swim   |   19 |
		|    2 | mary   |         1 | 2        | swim   |   22 |
		|    3 | tom    |         1 | 3        | swim   |   18 |
		|    4 | smith  |         2 | 1        | dance  |   23 |
		|    5 | bob    |         2 | 2        | dance  |   17 |
		|    6 | marna  |         2 | 3        | dance  |   25 |
		|    7 | obama  |         3 | 1        | sing   |   17 |
		|    8 | tomath |         3 | 2        | sing   |   26 |
		|    9 | max    |         3 | 3        | sing   |   26 |
		+------+--------+-----------+----------+--------+------+
		*/
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		System.out.println("id\tname\tclass_num\tclub_num\ttalent\tage\t");
		while(resultSet.next()){
			int id = resultSet.getInt(1);//本行第一列数据是int型
			String name = resultSet.getString(2);
			int class_num = resultSet.getInt(3);
			int club_num = resultSet.getInt(4);
			String talent = resultSet.getString(5);
			int age = resultSet.getInt(6);
			if(name.length() >= 4)
				System.out.println(id + "\t" + name + "\t\t" + class_num + "\t\t\t" +
						club_num + "\t\t" + talent + "\t" + age);
			else
				System.out.println(id + "\t" + name + "\t\t\t" + class_num + "\t\t\t" +
						club_num + "\t\t" + talent + "\t" + age);
		}

		//关闭
		resultSet.close();//结果集要关闭
		statement.close();//数据库操作连接要关闭
		connection.close();//数据库连接关闭

	}
}
