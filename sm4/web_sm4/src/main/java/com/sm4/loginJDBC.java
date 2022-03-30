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

public class loginJDBC extends HttpServlet {

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String name = (String) req.getParameter("name");
		String pass = (String) req.getParameter("pass");
		
		String mainKey = "";
		String passRan = "";
		String theKey = "";

		///
		boolean flag = false;
		int id = -1;
		int role = -1;
		String pass_cipher = "";
		String pass_plain = "";

		
		Connection con = null;
		try {
			con = new GetConnection().getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			//遍历user表取id主键，role角色
			String sql = "select * from user where name=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				id = rs.getInt("id");
				role = rs.getInt("role");

			} else {
				System.out.println("用户名不存在！");
				resp.getWriter().write("<script > alert('user is unregistered!');</script>");
				resp.setHeader("refresh", "0;url=index.jsp");
				return;

			}

			//遍历theKey表取主key
			String sql1 = "select * from theKey where id=1";
			PreparedStatement pstmt1 = con.prepareStatement(sql1);
			ResultSet rs1 = pstmt1.executeQuery();
			if (rs1.next()) {
				mainKey = rs1.getString("mainKey");

			}

			//遍历pass表取passRan与pass_cipher
			String sql2 = "select * from pass where id=?";
			PreparedStatement pstmt2 = con.prepareStatement(sql2);
			pstmt2.setInt(1, id);
			ResultSet rs2 = pstmt2.executeQuery();
			if (rs2.next()) {
				passRan = rs2.getString("passRan");
				pass_cipher = rs2.getString("pass_cipher");

			}
			//System.out.println("passRan：" + passRan);

			//生成key1
			try {
				theKey = Sm4Util.encryptEcb(mainKey, passRan);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String key1 = theKey.substring(0, 32);
			System.out.println("key1为：" + key1);

			//解密数据库中pass密文，得pass_plain				
			try {
				pass_plain = Sm4Util.decryptEcb(key1, pass_cipher);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//解密
			System.out.println("pass解密后：" + pass_plain);
			System.out.println("浏览器端传来的pass：" + pass);

			if (pass.equals(pass_plain)) {
				flag = true;

			} else {
				flag = false;

			}
			System.out.println("---------flag：" + flag);
			pstmt.close();
			pstmt1.close();
			pstmt2.close();
			rs.close();
			rs1.close();
			rs2.close();
			con.close();

			if (flag) {
				if (role == 0) {
			//普通用户
					
			req.setAttribute("name", name);
			req.setAttribute("pass_id", id);
			//resp.sendRedirect("checkJDBC.do");
			req.getRequestDispatcher("/user/checkJDBC.do").forward(req, resp);

				} else {
			//管理员
			HttpSession session = req.getSession();
			//req.setAttribute("name", name);
			//req.getRequestDispatcher("administrator/manage.jsp").forward(req, resp);

			session.setAttribute("name", name);
			resp.sendRedirect("administrator/manage.jsp");

				}

			} else {
				//response.setHeader("refresh","2;url=login.jsp");
				//out.println("错误，停留两秒返回登录界面");

				resp.getWriter().write("<script > alert('user or pass is wrong!');</script>");
				resp.setHeader("refresh", "0;url=index.jsp");


			}

			//System.out.println("------数据库操作成功！");
		} catch (SQLException e) {
			System.out.println("------数据库操作失败！");
			e.printStackTrace();
		}
	}
}
