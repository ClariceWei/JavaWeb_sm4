package com.sm4;

import javax.servlet.http.HttpSession;

public class TokenTools {
	
	public TokenTools(){}
	/* *功能：生成token令牌，并保存在session中
	 * *参数：tokenname是在session中token的名称
	 * *	session是要存储token的目标session
	 * *返回值：返回session字符串
	 * */
	public static String setToken(String tokenname, HttpSession session) throws Exception
	{
		String token = TokenUtil.getInstance().makeToken();
		session.setAttribute(tokenname, token);
		return token;
	}
	/* *功能：验证token是否存在
	 * *参数：tokenname是在session中token的名称
	 * *	token是从表单中提取的令牌值
	 * *	session是存储token的目标session
	 * *返回值：如果令牌正确返回true，反之为false
	 * */
	public static boolean verifyToken(String tokenname, String token,  HttpSession session)
	{
		if(session.getAttribute(tokenname)==null)
		{
			return false;
		}
		
		String tokenInsession = session.getAttribute(tokenname).toString();
		//如果验证令牌通过，就重置session属性
		if(tokenInsession.equals(token))
		{
			session.removeAttribute(tokenname);
			return true;
		}
		return false;
	}
 
}
