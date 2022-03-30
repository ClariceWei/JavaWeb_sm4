package com.sm4;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class updateJDBCdo extends HttpServlet{
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setHeader("content-type","text/html;charset=UTF-8");

		String name0 = req.getParameter("name");
		String name = new String(name0.getBytes("iso8859-1"),"utf-8");
		
		String pass0 = req.getParameter("pass");
		String pass = new String(pass0.getBytes("iso8859-1"),"utf-8");
		
		String phone0 = req.getParameter("phone");
		String phone = new String(phone0.getBytes("iso8859-1"),"utf-8");
		
		int role = Integer.parseInt(req.getParameter("role"));
		int id = Integer.parseInt(req.getParameter("id"));
		String theKey = "";
		String mainKey = "";
		Connection con = null;
		try {
			con = new GetConnection().getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			//更新user表
			String sql11 = "UPDATE user SET name=?, role=? WHERE id=?";
			PreparedStatement pstmt11 = con.prepareStatement(sql11);
			pstmt11.setString(1, name);
			pstmt11.setInt(2, role);
			pstmt11.setInt(3, id);
			pstmt11.execute();

			//遍历theKey表寻找主key
			String sql0 = "select * from theKey where id=1";
			PreparedStatement pstmt0 = con.prepareStatement(sql0);
			ResultSet rs0 = pstmt0.executeQuery();
			if (rs0.next()) {
				mainKey = rs0.getString("mainKey");

			}

			//随机生成新随机数
			String key0 = UUID.randomUUID().toString();
			String newRan = key0.replaceAll("-", "");
			System.out.println("随机生成32位秘钥为：" + newRan);

			//将newRan与主key作用生成秘钥Key1
			try {
				theKey = Sm4Util.encryptEcb(mainKey, newRan);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("theKey为：" + theKey);
			//截取theKey前32位为key1
			String key1 = theKey.substring(0, 32);
			System.out.println("key1为：" + key1);

			//加密pass与phone
			String pass_cipher = null;
			String phone_cipher = null;
			try {
				pass_cipher = Sm4Util.encryptEcb(key1, pass);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				phone_cipher = Sm4Util.encryptEcb(key1, phone);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//更新pass表
			String sql22 = "UPDATE pass SET pass_cipher=?,passRan=?,phone_cipher=? WHERE id=?";
			PreparedStatement pstmt22 = con.prepareStatement(sql22);
			pstmt22.setString(1, pass_cipher);
			pstmt22.setString(2, newRan);
			pstmt22.setString(3, phone_cipher);
			pstmt22.setInt(4, id);
			pstmt22.execute();

			pstmt11.close();
			pstmt22.close();

			con.close();

			//out.println(name + "------修改成功！");
			resp.getWriter().write("<script > alert('update success!');</script>");
			resp.setHeader("refresh", "0;url=manage.jsp");
		} catch (SQLException e) {
			System.out.println("------数据库操作失败！");
			e.printStackTrace();
		}
	}

}
