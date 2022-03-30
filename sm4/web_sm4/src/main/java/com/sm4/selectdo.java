package com.sm4;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class selectdo extends HttpServlet{

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		int id = Integer.parseInt(req.getParameter("id"));
		
		//HttpSession session = ((HttpServletRequest) req).getSession();
		//int id = (int) session.getAttribute("id");
		
		System.out.println("selectdo.do接收到的id:"+id);
		req.setCharacterEncoding("UTF-8");
		
		Connection con = null;
		try {
			con = new GetConnection().getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {

			String sql = "UPDATE checking SET checkDo=? WHERE id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "通过");
			pstmt.setInt(2, id);
			pstmt.execute();
			pstmt.close();
			con.close();
			//System.out.println("------修改成功！");
			resp.getWriter().write("<script > alert('verify success!');</script>");
			resp.setHeader("refresh", "0;url=select.jsp");
		} catch (SQLException e) {
			System.out.println("------数据库操作失败！");
			e.printStackTrace();
		}
		
	}
}
