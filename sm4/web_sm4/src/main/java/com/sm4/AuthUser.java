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


public class AuthUser implements Filter {

	public void destroy() {
		System.out.println("------destroy---");

	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("----doFilter---");
		
		//HttpServletRequest request = (HttpServletRequest) req;
		//HttpServletResponse response = (HttpServletResponse) resp;
		//HttpSession session = request.getSession();
		
		String user = req.getParameter("name");
		HttpSession session = ((HttpServletRequest) req).getSession();
		String user2 = (String) session.getAttribute("name");
		
		if(user==null && user2==null){
		System.out.println("-用户名为空-");
		((HttpServletResponse) resp).sendRedirect("../index.jsp");
		}else{
			chain.doFilter(req, resp);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("-------init------");
	}

}
