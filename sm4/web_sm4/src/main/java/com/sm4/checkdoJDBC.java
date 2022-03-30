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

public class checkdoJDBC extends HttpServlet {

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setHeader("content-type", "text/html;charset=UTF-8");

		String name0 = req.getParameter("name");
		String name = new String(name0.getBytes("iso8859-1"), "utf-8");

		String clock0 = req.getParameter("clock");
		String clock = new String(clock0.getBytes("iso8859-1"), "utf-8");

		String addr0 = req.getParameter("addr");
		String addr = new String(addr0.getBytes("iso8859-1"), "utf-8");

		String state0 = req.getParameter("state");
		String state = new String(state0.getBytes("iso8859-1"), "utf-8");

		String phone0 = req.getParameter("phone");
		String phone = new String(phone0.getBytes("iso8859-1"), "utf-8");

		int goArea = Integer.parseInt(req.getParameter("goArea"));

		String temper1 = req.getParameter("temper");
		System.out.println("req到的temper1：" + temper1);

		if (temper1 == null || temper1.equals("")) {
			resp.getWriter().write("<script > alert('temper is null!');</script>");
			resp.setHeader("refresh", "0;url=/sm4/user/check.jsp");
		} else {
			boolean flag = false;

			try {
				Double.parseDouble(temper1);
				flag = true;
			} catch (NumberFormatException e) {
				flag = false;
			}

			if (flag) {
				double temper = Double.parseDouble(temper1);

				String isCheckd = "";
				String CheckDo = "";
				int passid = 1;

				String mainKey = "";
				String theKey = "";

				Connection con = null;
				try {
					con = new GetConnection().getConnection();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {

					String SgoArea = goArea + "";
					System.out.println("SgoArea:" + SgoArea);
					if (name.equals("") || addr.equals("") || phone.equals("") || clock.equals("") || temper1.equals("")
							|| SgoArea.equals("") || state.equals("")) {
						resp.getWriter().write("<script > alert('something is null!');</script>");
						// out.println(name + "------有空项未填写！");
						resp.setHeader("refresh", "0;url=/sm4/user/check.jsp");

					} else {

						// 遍历key表，确定有无主key
						String sqlQuery0 = "select * from theKey where id=2";
						PreparedStatement pstmt0 = con.prepareStatement(sqlQuery0);
						ResultSet rs0 = pstmt0.executeQuery();
						// 当第一个用户个人签到信息提交到数据库，生成一个info_key，存入数据库的theKey表
						if (rs0.next()) {
							System.out.println("key表内已有mainKey");
							// System.out.println("mainKey为：" + mainKey);
						} else {
							// 随机生成主key
							String key0 = UUID.randomUUID().toString();
							mainKey = key0.replaceAll("-", "");
							System.out.println("随机生成32位秘钥为：" + mainKey);

							// 初始化pass表，存入主key
							String sql00 = "INSERT INTO theKey (id, mainKey) VALUES (NULL, ?)";
							PreparedStatement pstmt00 = con.prepareStatement(sql00);
							pstmt00.setString(1, mainKey);
							pstmt00.execute();

						}

						// 加密addr,phone字段,此两字段属于学生隐私
						// 生成随机数
						String ran0 = UUID.randomUUID().toString();
						String infoRan = ran0.replaceAll("-", "");
						System.out.println("随机生成32位秘钥为：" + infoRan);

						// 遍历theKey表取主秘钥mainKey
						String sql1 = "select * from theKey where id=2";
						PreparedStatement pstmt1 = con.prepareStatement(sql1);
						ResultSet rs1 = pstmt1.executeQuery();
						if (rs1.next()) {
							mainKey = rs1.getString("mainKey");

						}
						System.out.println("mainKey为：" + mainKey);

						// 将passRan与mainKey作用生成秘钥Key2
						try {
							theKey = Sm4Util.encryptEcb(mainKey, infoRan);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("theKey为：" + theKey);
						// 截取theKey前32位为key1
						String key2 = theKey.substring(0, 32);
						System.out.println("key2为：" + key2);

						// 加密
						String addr_cipher = null;
						String phone_cipher = null;
						try {
							addr_cipher = Sm4Util.encryptEcb(key2, addr);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						try {
							phone_cipher = Sm4Util.encryptEcb(key2, phone);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("addr加密后：" + addr_cipher);
						System.out.println("phone加密后：" + phone_cipher);

						// isCheckd与CheckDo字段判定
						if (state.equals("健康") && temper <= 37.0 && goArea == 0) {
							isCheckd = "无需审核";
							CheckDo = "通过";

						} else {
							isCheckd = "需审核";
							CheckDo = "未通过";

						}

						// 将加密后的学生个人信息与infoRan填入state表
						String sqlQuery = "INSERT INTO state (id, name, addr_cipher, phone_cipher, clock, temper, goArea, state, infoRan) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?)";
						PreparedStatement pstmt01 = con.prepareStatement(sqlQuery);
						pstmt01.setString(1, name);
						pstmt01.setString(2, addr_cipher);
						pstmt01.setString(3, phone_cipher);
						pstmt01.setString(4, clock);
						pstmt01.setDouble(5, temper);
						pstmt01.setInt(6, goArea);
						pstmt01.setString(7, state);
						pstmt01.setString(8, infoRan);

						System.out.println("isCheckd:" + isCheckd);

						// 将学生姓名name,是否需审核isCheckd，审核是否通过checkDo存入checking表
						String sqlQuery1 = "INSERT INTO checking (id, name, isCheckd, CheckDo) VALUES (NULL, ?, ?, ?)";
						PreparedStatement pstmt02 = con.prepareStatement(sqlQuery1);
						pstmt02.setString(1, name);
						pstmt02.setString(2, isCheckd);
						pstmt02.setString(3, CheckDo);

						pstmt01.execute();
						pstmt02.execute();
						pstmt01.close();
						pstmt02.close();
						con.close();

						resp.getWriter().write("<script > alert('submit success!');</script>");
						resp.setHeader("refresh", "0;url=/sm4/logout.do");
					}

				} catch (SQLException e) {
					e.printStackTrace();
					// System.out.println("------数据库操作失败！");
				}
			} else {
				resp.getWriter().write("<script > alert('temper is wrong!');</script>");
				resp.setHeader("refresh", "0;url=/sm4/user/check.jsp");
			}
		}
	}
}
