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

public class updateJDBC extends HttpServlet{
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String name = (String) req.getParameter("name");
		
		System.out.println("name" + name);

		int id = -1;
		int role = -1;

		String theKey = "";
		String pass_cipher = "";

		String pass = "";
		String phone = "";
		String phone_cipher = "";
		String mainKey = "";
		String passRan = "";
		
		Connection con = null;
		try {
			con = new GetConnection().getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		try {
		//遍历theKey表取主key
			String sql0 = "select * from theKey where id=1";
			PreparedStatement pstmt0 = con.prepareStatement(sql0);
			ResultSet rs0 = pstmt0.executeQuery();
			if (rs0.next()) {
				mainKey = rs0.getString("mainKey");

			}
			
			//遍历user表取用户id
			String sql = "select * from user where name=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				id = rs.getInt("id");
				role = rs.getInt("role");

			}
			System.out.println("id:" + id);
			System.out.println("role:" + role);

			//遍历pass表取密文信息
			String sql1 = "select * from pass where id=?";
			PreparedStatement pstmt1 = con.prepareStatement(sql1);
			pstmt1.setInt(1, id);
			ResultSet rs1 = pstmt1.executeQuery();
			while (rs1.next()) {
				pass_cipher = rs1.getString("pass_cipher");
				phone_cipher = rs1.getString("phone_cipher");
				passRan = rs1.getString("passRan");

			}

			try {
				theKey = Sm4Util.encryptEcb(mainKey, passRan);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String key1 = theKey.substring(0, 32);
			System.out.println("key1为：" + key1);

			//解密密码
			try {
				pass = Sm4Util.decryptEcb(key1, pass_cipher);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				phone = Sm4Util.decryptEcb(key1, phone_cipher);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			req.setAttribute("name", name);
			req.setAttribute("pass", pass);
			req.setAttribute("phone", phone);
			req.setAttribute("role", role);
			req.setAttribute("id", id);
			//resp.sendRedirect("checkJDBC.do");
			req.getRequestDispatcher("/administrator/update.jsp").forward(req, resp);

		} catch (SQLException e) {
			System.out.println("------数据删除失败！");
			e.printStackTrace();
		}
	}
}
