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
import javax.servlet.http.HttpSession;

public class checkJDBC extends HttpServlet {

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setHeader("content-type","text/html;charset=UTF-8");
		String name = req.getParameter("name");
		System.out.println("checkJDBCname为：" + name);
		
		String pass_id = req.getAttribute("pass_id").toString();
		int id = Integer.parseInt(pass_id);
		String theKey = "";
		String phone_cipher = "";
		String phone_plain = "";
		String passRan = "";
		String mainKey = "";
	
		Connection con = null;
		try {
			con = new GetConnection().getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			//解密phone_cipher为phone_plain自动填表			
			//遍历pass表取phone_pass与随机数
			String sql = "select * from pass where id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				passRan = rs.getString("passRan");
				phone_cipher = rs.getString("phone_cipher");

			}
			//System.out.println("key：" + theKey);
			
			//遍历theKey表取主key
			String sql2 = "select * from theKey where id=1";
			PreparedStatement pstmt2 = con.prepareStatement(sql2);
			ResultSet rs2 = pstmt2.executeQuery();
			if (rs2.next()) {
				mainKey = rs2.getString("mainKey");

			}

			//用主key与passRan作用生成key1
			try {
				theKey = Sm4Util.encryptEcb(mainKey, passRan);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String key1 = theKey.substring(0,32);
			System.out.println("key1为：" + key1);
			
			//解密数据库中phone密文，得phone_plain				
			try {
				phone_plain = Sm4Util.decryptEcb(key1, phone_cipher);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//解密
			System.out.println("phone解密后：" + phone_plain);

			pstmt.close();
			rs.close();
			con.close();
			System.out.println("------数据库操作成功！");
			
			//req.setAttribute("name", name);
			//req.setAttribute("phone_plain", phone_plain);
			//req.getRequestDispatcher("/user/check.jsp").forward(req, resp);
			
			HttpSession session = req.getSession();
			session.setAttribute("name", name);
			session.setAttribute("phone_plain", phone_plain);
			resp.sendRedirect("user/check.jsp");
			



		} catch (SQLException e) {
			System.out.println("------数据库操作失败！");
			e.printStackTrace();
		}

		
	}
}
