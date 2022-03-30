<%@page import="java.sql.*" pageEncoding="utf-8"%>
<%@page import="com.sm4.GetConnection"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>管理用户</title>
<link rel="stylesheet" href="../css/layui.css" media="all">
</head>
<body>
	<%
		String Mname = (String) session.getAttribute("name");
	%>

	<div class="layui-layout layui-layout-admin">
		<div class="layui-header">
			<div class="layui-logo">个人信息签到表</div>
			<!-- 头部区域（可配合layui 已有的水平导航） -->
			<ul class="layui-nav layui-layout-left">


			</ul>
			<ul class="layui-nav layui-layout-right">
				<li class="layui-nav-item"><a href="javascript:;"> <img
						src="../a.png" class="layui-nav-img"> <%=Mname%>
				</a></li>
				<li class="layui-nav-item" onclick="document.getElementById('myform').submit()">
				<form method="post" action="/sm4/logout.do" id="myform">
				退出登录
				</form>	
				</li>
			</ul>
		</div>

		<div class="layui-side layui-bg-black">
			<div class="layui-side-scroll">
				<!-- 左侧导航区域（可配合layui已有的垂直导航） -->


				<ul class="layui-nav layui-nav-tree" lay-filter="test">
					<li class="layui-nav-item layui-nav-itemed"><a class=""
						href="javascript:;">管理员页面</a>
						<dl class="layui-nav-child">
							<dd>
							<a onclick="document.getElementById('myform').submit()" >
				<form method="post" action="/sm4/logout.do" id="myform">
				登录页面
				</form>	
								</a>
							</dd>
							<dd>
								<a href="javascript:;">管理页面</a>
							</dd>
							<dd>
								<a href="/sm4/administrator/select.jsp">审核页面</a>
							</dd>
						</dl></li>
					<li class="layui-nav-item"><a href="javascript:;">学生页面</a>
						<dl class="layui-nav-child">
							<dd>
								<a href="javascript:;" title="请以学生身份登录">登录页面</a>
							</dd>
							<dd>
								<a href="javascript:;" title="请以学生身份登录">签到页面</a>
							</dd>

						</dl></li>

				</ul>
			</div>
		</div>

		<div class="layui-body">
			<!-- 内容主体区域 -->
			<div style="padding: 15px;">
				<fieldset class="layui-elem-field layui-field-title"
					style="margin-top: 50px;">
					<legend> 管理用户 </legend>
				</fieldset>

				<table class="layui-table" lay-skin="line">
					<colgroup>
						<col width="150">
						<col width="150">
						<col width="200">
						<col>
					</colgroup>
					<thead>
						<tr>
							<th>序号</th>
							<th>用户名</th>
							<th>角色</th>
							<th>更新</th>
							<th>删除</th>
						</tr>
					</thead>

					<%
						request.setCharacterEncoding("UTF-8");

					int id = -1;
					int role = -1;
					String isCheckd = "";
					String name = "";

					Connection con = null;
					try {
						con = new GetConnection().getConnection();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					try {
						//遍历user表，取id	
						String sql = "select * from user";
						PreparedStatement pstmt = con.prepareStatement(sql);
						ResultSet rs = pstmt.executeQuery();

						while (rs.next()) {
							id = rs.getInt("id");
							name = rs.getString("name");
							role = rs.getInt("role");
					%>
					<tbody>
						<tr>
							<td><%=id%></td>
							<td><%=name%></td>
							<td>
								<%
									if (role == 0) {
									out.println("学生");
								} else {
									out.println("管理员");
								}
								%>
							</td>


							<td><a href="/sm4/administrator/updateJDBC.do?name=<%=name%>">更新</a></td>

							<td><a href="/sm4/administrator/deleteJDBC.do?name=<%=name%>">删除</a></td>


						</tr>
					</tbody>

					<%
						}

					pstmt.execute();
					pstmt.close();
					con.close();

					System.out.println("------数据库操作成功！");
					} catch (SQLException e) {
					System.out.println("------数据库操作失败！");
					e.printStackTrace();
					}
					%>
				</table>


				<div class="layui-form-item">
					<div class="layui-input-block">
					
						<button type="button" class="layui-btn layui-btn-primary">
							<a href="../index.jsp">返回登录</a>
						</button>
						<button type="button" class="layui-btn layui-btn-primary">
						<a href="/sm4/administrator/select.jsp">转至审核</a>
						</button>
					</div>
				</div>

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
