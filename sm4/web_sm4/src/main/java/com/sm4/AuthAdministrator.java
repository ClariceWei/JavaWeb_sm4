package com.sm4;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class AuthAdministrator implements Filter {

	public void destroy() {
		System.out.println("------destroy---");

	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("----doFilter---");
		HttpSession session = ((HttpServletRequest) req).getSession();
		String administrator2 = (String) session.getAttribute("name");
	
		String administrator = req.getParameter("name");

		
		if(administrator==null && administrator2==null){
			System.out.println("-用户名为空-");
			((HttpServletResponse) resp).sendRedirect("../index.jsp");
		}else{
			System.out.println("-用户名不为空-");
			chain.doFilter(req, resp);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("-------init------");
	}

}
