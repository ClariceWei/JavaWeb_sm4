package com.sm4;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class checkUser extends HttpServlet {

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String name = (String) req.getParameter("name");

		Connection con = null;
		try {
			con = new GetConnection().getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			String sqlQuery = "select * from user where name=?";
			PreparedStatement pstmt = con.prepareStatement(sqlQuery);
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			PrintWriter out = resp.getWriter();

			if (rs.next()) {
				out.print(1);

				// System.out.println("1！");

			} else {
				out.print(0);
				// System.out.println("0！");

			}

			rs.close();
			pstmt.close();
			con.close();

		} catch (SQLException e) {
			System.out.println("------数据库操作失败！");
		}

	}
}
