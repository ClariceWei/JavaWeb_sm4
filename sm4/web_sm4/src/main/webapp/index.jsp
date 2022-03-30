<%@page pageEncoding="utf-8" contentType="text/html;charset=UTF-8"
	import="java.util.*"%>
<%@page import="com.sm4.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>login</title>

<link rel="stylesheet" href="css/style.css">

</head>
<script>
	function checkUser() {
		var name = document.getElementById("name").value;
		//alert(name);

		$.post("checkUser.do", {
			"name" : name
		}, function(data) {
			//alert(data);
			if (data == 1) {
				$("#userInfo").html(name + "已经注册，请换一个用户名！").css({
					"color" : "red"
				});
			} else {
				$("#userInfo").html(name + "可以注册！").css({
					"color" : "green"
				});
			}

		});

	}

	function checkUser2() {
		var name = document.getElementById("name2").value;
		//alert(name);

		$.post("checkUser.do", {
			"name" : name
		}, function(data) {
			//alert(data);
			if (data == 1) {
				$("#userInfo2").html(name + "已经注册，可以登录！").css({
					"color" : "green"
				});
			} else {
				$("#userInfo2").html(name + "尚未注册，请先注册！").css({
					"color" : "red"
				});
			}

		});

	}
</script>

<body>
	<%
		/*设置token令牌*/
	String token = TokenTools.setToken("regist_token", session);
	//System.out.println("session: " + session);
	//System.out.println("token: " + token);
	%>
	
	<!-- 登录页面 -->
	<div class="container">
		<div class="login-box">
			<div class="apple-btn login-apple">
				<li class="red-btn"></li>
				<li class="yellow-btn"></li>
				<li class="green-btn"></li>
			</div>
			<form method="post" action="loginJDBC.do" id="myform">
				<div class="title">登录</div>
				<div class="input1">
					<input type="text" id="name2" name="name" onblur="checkUser2()"
						placeholder="请输入您的账号"><span id="userInfo2"></span>
				</div>
				<div class="input1">
					<input type="password" id="login-password" name="pass"
						placeholder="请输入您的密码">
				</div>
				<div class="btn login-btn"
					onclick="document.getElementById('myform').submit()">
					<span>登录</span>
				</div>
			</form>
			<div class="change-box login-change">
				<div class="change-btn toSign">
					<span>去注册</span>
				</div>
			</div>
		</div>

		<!-- 注册页面 -->
		<div class="sign-box">
			<div class="apple-btn sign-apple">
				<li class="red-btn"></li>
				<li class="yellow-btn"></li>
				<li class="green-btn"></li>
			</div>
			<form method="post" action="regist_do.do" id="myform1">
				<div class="title">注册</div>
				<div class="input">
					<input type="text" name="name" id="name" onblur="checkUser()"
						placeholder="请输入您的账号"><span id="userInfo"></span> 
<input type="hidden" name="regist_token" value="<%=token%>">
				</div>
				<div class="input">
					<input type="password" id="sign-password" name="pass"
						placeholder="请输入您的密码">
				</div>
				<div class="input">
					<input type="text" id="sign-phone" name="phone"
						placeholder="请输入您的手机号">
				</div>
				<!--
						</div>
						<div class="input3">

							<input type="radio" name="role" value="1" title="管理员" checked="">
							<input type="radio" name="role" value="0" title="用户">
						</div>
  -->

				<div class="btn sign-btn"
					onclick="document.getElementById('myform1').submit()">

					<span>注册</span>
				</div>
			</form>
			<div class="change-box sign-change">
				<div class="change-btn toLogin">
					<span>去登陆</span>
				</div>
			</div>

		</div>


		<script src="js/jquery.min.js"></script>
		<script src="js/script.js"></script>
		<script src="js/jquery.js"></script>
</body>
</html>
