package com.sm4;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class registJDBC extends HttpServlet {

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String name = (String) req.getAttribute("name");
		String pass = (String) req.getAttribute("pass");
		String phone = (String) req.getAttribute("phone");

		if (name.equals("") || pass.equals("") || phone.equals("")) {
			resp.getWriter().write("<script > alert('Personal Information is null!');</script>");
			resp.setHeader("refresh", "0;url=/sm4/index.jsp");
		} else {
			/*
			 * String name,pass,phone; name = req.getParameter("name"); phone =
			 * req.getParameter("phone"); pass = req.getParameter("pass");
			 */
			System.out.println("pass为：" + pass);
			int role = 0;
			String pass_plain = "";
			String phone_plain = "";
			String mainKey = "";
			String theKey = "";

			Connection con = null;
			try {
				try {
					con = new GetConnection().getConnection();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				String sqlQuery = "select * from user where name=?";
				PreparedStatement pstmt = null;
				try {
					pstmt = con.prepareStatement(sqlQuery);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					pstmt.setString(1, name);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ResultSet rs = null;
				try {
					rs = pstmt.executeQuery();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					if (rs.next()) {
						System.out.println(name + "----已经注册，请换一个用户名！");
						resp.getWriter().write("<script > alert('already registed,please change it');</script>");
						resp.setHeader("refresh", "0;url=index.jsp");
					} else {
						// 遍历key表，确定有无主key
						String sqlQuery0 = "select * from theKey where id = 1";
						PreparedStatement pstmt0 = con.prepareStatement(sqlQuery0);
						ResultSet rs0 = pstmt0.executeQuery();
						// 当第一个用户注册到数据库，生成一个pass_key，存入数据库的theKey表
						if (rs0.next()) {
							System.out.println("key表内已有mainKey1");
							System.out.println("mainKey为：" + mainKey);
						} else {
							// 随机生成主key
							String key0 = UUID.randomUUID().toString();
							mainKey = key0.replaceAll("-", "");
							System.out.println("随机生成32位秘钥为：" + mainKey);

							// 初始化pass表，存入主key
							String sql00 = "INSERT INTO theKey(id, mainKey) VALUES (NULL, ?)";
							PreparedStatement pstmt00 = con.prepareStatement(sql00);
							pstmt00.setString(1, mainKey);
							pstmt00.execute();

						}

						// 加密pass和phone，生成pass_cipher和phone_cipher
						// 生成随机数passRan，与key表主秘钥pass_key相作用生成秘钥key1，将随机数passRan存入数据库
						String ran0 = UUID.randomUUID().toString();
						String passRan = ran0.replaceAll("-", "");
						System.out.println("随机生成32位随机数为：" + passRan);

						// 遍历theKey表取主秘钥pass_key
						String sql1 = "select * from theKey where id=1";
						PreparedStatement pstmt1 = con.prepareStatement(sql1);
						ResultSet rs1 = pstmt1.executeQuery();
						if (rs1.next()) {
							mainKey = rs1.getString("mainKey");

						}
						System.out.println("mainKey为：" + mainKey);
						System.out.println("-----调用servlet成功-----");
						// 将passRan与pass_key作用生成秘钥Key1
						theKey = Sm4Util.encryptEcb(mainKey, passRan);
						System.out.println("theKey为：" + theKey);
						// 截取theKey前32位为key1
						String key1 = theKey.substring(0, 32);
						System.out.println("key1为：" + key1);

						// 用key1与pass,phone明文作用加密
						String pass_cipher = Sm4Util.encryptEcb(key1, pass);// sm4加密
						String phone_cipher = Sm4Util.encryptEcb(key1, phone);// sm4加密

						System.out.println("pass加密后：" + pass_cipher);
						System.out.println("phone加密后：" + phone_cipher);

						pass_plain = Sm4Util.decryptEcb(key1, pass_cipher);// 解密
						phone_plain = Sm4Util.decryptEcb(key1, phone_cipher);

						System.out.println("pass解密后：" + pass_plain);
						System.out.println("phone解密后：" + phone_plain);

						// 存储至user表
						String sql3 = "INSERT INTO user (id, name, role) VALUES (NULL, ?, ?)";
						PreparedStatement pstmt3 = con.prepareStatement(sql3);
						pstmt3.setString(1, name);
						pstmt3.setInt(2, role);
						pstmt3.execute();

						// 存至pass表
						String sql2 = "INSERT INTO pass (id, pass_cipher, phone_cipher, passRan) VALUES (NULL, ?, ?, ?)";
						PreparedStatement pstmt2 = con.prepareStatement(sql2);
						pstmt2.setString(1, pass_cipher);
						pstmt2.setString(2, phone_cipher);
						pstmt2.setString(3, passRan);
						pstmt2.execute();

						pstmt1.close();
						pstmt2.close();

						System.out.println(name + "------注册成功！");
						resp.getWriter().write("<script > alert('regist success!');</script>");
						resp.setHeader("refresh", "0;url=/sm4/index.jsp");

					}

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} finally {
				try {
					// 关闭Connection链接
					if (con != null) {
						con.close();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
