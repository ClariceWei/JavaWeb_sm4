package com.sm4;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class deleteJDBC extends HttpServlet{

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String name = (String) req.getParameter("name");
		
		int id = -1;
		boolean flag = false;
		Connection con = null;
		try {
			con = new GetConnection().getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			//遍历user表取用户名id
			
			String sql = "select * from user where name = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				id = rs.getInt("id");

			}
			
			System.out.println("id:"+id+",name:"+name);
			

			//删除pass表
			String sqlQuery = "delete from pass where id=?";
			PreparedStatement pstmt1 = con.prepareStatement(sqlQuery);
			pstmt1.setInt(1, id);
			pstmt1.execute();

			//删除user表
			String sqlQuery2 = "delete from user where id=?";
			PreparedStatement pstmt2 = con.prepareStatement(sqlQuery2);
			pstmt2.setInt(1, id);
			pstmt2.execute();

			pstmt1.close();
			pstmt2.close();

			con.close();
			resp.getWriter().write("<script > alert('delete success!');</script>");
			resp.setHeader("refresh", "0;url=manage.jsp");
			//out.println("------数据删除成功！");

		} catch (SQLException e) {
			System.out.println("------数据删除失败！");
			e.printStackTrace();
		}
	}
}
