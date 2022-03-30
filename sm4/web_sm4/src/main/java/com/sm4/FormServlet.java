package com.sm4;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FormServlet extends HttpServlet {
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");

		String name, pass, phone, token;
		token = req.getParameter("regist_token");
		System.out.println("servlet token=" + token);

		// 验证token1是否合法
		HttpSession session = req.getSession();
		// System.out.println("servlet:
		// "+session.getAttribute("regist_token").toString());
		boolean pass0 = TokenTools.verifyToken("regist_token", token, session);

		if (pass0 == false) {
			System.out.println("token repeat submit!");

			req.setAttribute("msg", "请不要重复提交表单");
			req.getRequestDispatcher("/index.jsp").forward(req, resp);
			return;
		}
		System.out.println("----token合法！");
		name = req.getParameter("name");
		pass = req.getParameter("pass");
		phone = req.getParameter("phone");

		if (name == null || pass == null || phone == null) {
			String path = req.getContextPath();
			String basePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + path + "/";
			resp.sendRedirect(basePath + "index.jsp");
			System.out.println("----在这里1----");
			return;
		}

		req.setAttribute("name", name);
		req.setAttribute("pass", pass);
		req.setAttribute("phone", phone);
		req.setAttribute("token", token);
		System.out.println("name--"+name);
		System.out.println("----在这里2----");
		// response.sendRedirect("/index.jsp");
		req.getRequestDispatcher("/registJDBC.do").forward(req, resp);

		return;
	}

}
