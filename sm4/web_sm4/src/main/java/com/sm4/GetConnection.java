package com.sm4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class GetConnection {

	public Connection getConnection() throws ClassNotFoundException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//System.out.println("驱动加载成功！");
		} catch (ClassNotFoundException e) {
			//System.out.println("驱动加载失败！");
		}
		
		String dbUrl = "jdbc:mysql://127.0.0.1:3306/sm4";
		String dbUser = "root";
		String dbPass = "root";

		
		Connection con = null;
		try {
			con = DriverManager.getConnection(dbUrl,dbUser,dbPass);
			System.out.println("---数据库连接成功！");
		} catch (SQLException e) {
			System.out.println("---数据库连接失败！");
		}
		
		return con;
		
	}

}
