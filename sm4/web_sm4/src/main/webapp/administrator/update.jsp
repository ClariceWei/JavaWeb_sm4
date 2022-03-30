<%@page import="java.sql.*" pageEncoding="utf-8"%>
<%@page import="com.sm4.*"%>
<%@page import="java.util.UUID"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>修改用户信息</title>
		<link rel="stylesheet" href="../css/layui.css" media="all">
	</head>
	<body>

		<%
			request.setCharacterEncoding("UTF-8");
			String name = (String)request.getAttribute("name");
			String pass = (String)request.getAttribute("pass");
			String phone = (String)request.getAttribute("phone");
			int role = (int)request.getAttribute("role");
			int id = (int)request.getAttribute("id");
		%>
		<div class="layui-layout layui-layout-admin">
			<div class="layui-header">
				<div class="layui-logo">
					个人信息签到表
				</div>
				<!-- 头部区域（可配合layui 已有的水平导航） -->
				<ul class="layui-nav layui-layout-left">


				</ul>
				<ul class="layui-nav layui-layout-right">
					<li class="layui-nav-item">
						<a href="javascript:;"> <img src="../a.png"
								class="layui-nav-img"> <%=name%> </a>

					</li>
					<li class="layui-nav-item">
						<a href="../index.jsp">退出登录</a>
					</li>
				</ul>
			</div>

			<div class="layui-side layui-bg-black">
				<div class="layui-side-scroll">
					<!-- 左侧导航区域（可配合layui已有的垂直导航） -->


					<ul class="layui-nav layui-nav-tree" lay-filter="test">
						<li class="layui-nav-item layui-nav-itemed">
							<a class="" href="javascript:;">管理员页面</a>
							<dl class="layui-nav-child">
								<dd>
									<a href="../index.jsp">登录页面</a>
								</dd>
								<dd>
									<a href="/sm4/administrator/manage.jsp">管理页面</a>
								</dd>
								<dd>
									<a href="/sm4/administrator/select.jsp">审核页面</a>
								</dd>
							</dl>
						</li>
						<li class="layui-nav-item">

							<a href="javascript:;">学生页面</a>
							<dl class="layui-nav-child">
								<dd>
									<a href="javascript:;">登录页面</a>
								</dd>
								<dd>
									<a href="javascript:;">签到页面</a>
								</dd>

							</dl>

						</li>

					</ul>
				</div>
			</div>

			<div class="layui-body">
				<!-- 内容主体区域 -->
				<div style="padding: 15px;">

					<fieldset class="layui-elem-field layui-field-title"
						style="margin-top: 50px;">
						<legend>
							修改用户信息
						</legend>
					</fieldset>
					<form class="layui-form layui-form-pane" method="post"
						action="/sm4/administrator/updateJDBCdo.do">
						<div class="layui-form-item">
							<label class="layui-form-label">
								用户名
							</label>
							<div class="layui-input-block">
								<input type="text" name="name" autocomplete="off"
									placeholder="请输入姓名" class="layui-input" value="<%=name%>">
								<input type="hidden" name="id" value="<%=id%>"
									class="layui-input">

							</div>
						</div>

						<div class="layui-form-item">
							<label class="layui-form-label">
								密码
							</label>
							<div class="layui-input-block">
								<input type="text" name="pass" autocomplete="off"
									placeholder="请输入密码" class="layui-input" value="<%=pass%>">


							</div>
						</div>

						<div class="layui-form-item">
							<label class="layui-form-label">
								联系方式
							</label>
							<div class="layui-input-block">
								<input type="text" name="phone" autocomplete="off"
									placeholder="请输入联系方式" class="layui-input" value="<%=phone%>">
							</div>
						</div>

						<div class="layui-form-item">
							<label class="layui-form-label">
								角色
							</label>
							<div class="layui-input-block">
								<input type="text" name="role" autocomplete="off"
									placeholder="请输入用户角色" class="layui-input" value="<%=role%>">
							</div>
						</div>

						<div class="layui-form-item">
							<div class="layui-input-block">
								<button type="submit" class="layui-btn" lay-submit=""
									lay-filter="demo1">
									立即提交
								</button>
								<button type="reset" class="layui-btn layui-btn-primary">
									重置
								</button>
							</div>
						</div>

					</form>

				</div>
			</div>

			<div class="layui-footer">
				<!-- 底部固定区域 -->
				基于国密算法的校园疫情签到系统
			</div>
			<script src="../js/layui.js" charset="utf-8">
</script>
			<script src="../js/jquery.min.js">
</script>
	</body>
</html>
