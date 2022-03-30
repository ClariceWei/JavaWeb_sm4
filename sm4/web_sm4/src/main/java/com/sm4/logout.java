package com.sm4;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class logout extends HttpServlet {

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = ((HttpServletRequest) req).getSession();
		session.removeAttribute("name");
		((HttpServletResponse) resp).sendRedirect("/sm4/index.jsp");

	}

}
