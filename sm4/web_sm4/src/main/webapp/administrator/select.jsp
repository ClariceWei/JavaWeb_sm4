<%@page import="java.sql.*" pageEncoding="utf-8"%>
<%@page import="com.sm4.*"%>
<%@page import="java.util.UUID"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>审核页面</title>
<link rel="stylesheet" href="../css/layui.css" media="all">
<style>
.black_overlay {
	display: none;
	position: absolute;
	top: 0%;
	left: 0%;
	width: 100%;
	height: 100%;
	background-color: black;
	z-index: 1001;
	-moz-opacity: 0.8;
	opacity: .80;
	filter: alpha(opacity = 88);
}

.white_content {
	display: none;
	position: absolute;
	top: 20%;
	left: 15%;
	width: 70%;
	height: 60%;
	padding: 20px;
	border: 10px solid #393D49;
	background-color: white;
	z-index: 1002;
	overflow: auto;
}
</style>
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

				<li class="layui-nav-item"
					onclick="document.getElementById('myform').submit()">
					<form method="post" action="/sm4/logout.do" id="myform">
						退出登录</form>
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
								<a onclick="document.getElementById('myform').submit()">
									<form method="post" action="/sm4/logout.do" id="myform">
										登录页面</form>
								</a>
							</dd>
							<dd>
								<a href="/sm4/administrator/manage.jsp">管理页面</a>
							</dd>
							<dd>
								<a href="javascript:;">审核页面</a>
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
					<legend> 审核页面 </legend>
				</fieldset>
				<center>



					<table class="layui-table" lay-skin="line">
						<colgroup>
							<col width="150">
							<col width="150">
							<col width="200">
							<col>
						</colgroup>
						<thead>
							<tr>
								<th>编号</th>
								<th>用户名</th>
								<th>需审核否</th>
							</tr>
						</thead>
						<tbody>

							<%
								String isCheckd = "";
							int id = -1;

							String addr = "";
							String phone = "";
							String clock = "";
							String addr_cipher = "";
							String phone_cipher = "";
							String infoKey = "";
							String state = "";
							String checkDo = "";
							int goArea = -1;
							double temper = -1.0;
							String mainKey = "";
							String infoRan = "";
							String theKey = "";
							String name = "";

							Connection con = null;
							try {
								con = new GetConnection().getConnection();
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							try {
								//遍历checking取checkDo字段
								String sql = "select * from checking order by checking.id";
								PreparedStatement pstmt = con.prepareStatement(sql);
								ResultSet rs = pstmt.executeQuery();
								while (rs.next()) {
									id = rs.getInt("id");
									name = rs.getString("name");
									checkDo = rs.getString("checkDo");
							%>
							<tr>
								<td><%=id%></td>
								<td><%=name%></td>

								<td>
									<%
										if (checkDo.equals("通过")) {
										out.println("审核通过");
									} else {
										out.println("需审核 审核：" + checkDo + "");
									%> <%
 	}

 }
 pstmt.execute();
 %>
								</td>
							</tr>
						</tbody>
					</table>

					<div class="layui-btn-container">
						<button type="button"
							class="layui-btn layui-btn-primary layui-btn-radius">
							<a href="JavaScript:void(0)" onclick="openDialog()">点击审核</a>
						</button>


						<button type="button"
							class="layui-btn layui-btn-primary layui-btn-radius">

							<a href="/sm4/administrator/manage.jsp">返回管理</a>
						</button>


					</div>
			</div>
		</div>
		<div class="layui-footer">
			<!-- 底部固定区域 -->
			基于国密算法的校园疫情签到系统
		</div>

		<!-- 悬浮窗口 -->
		<div id="light" class="white_content">

			<fieldset class="layui-elem-field layui-field-title"
				style="margin-top: 50px;">
				<legend> 个人信息 </legend>
			</fieldset>


			<table class="layui-table" lay-even="" lay-skin="row">
				<colgroup>
					<col width="60">
					<col width="60">
					<col width="70">
					<col width="75">
				</colgroup>
				<thead>

					<tr>
						<td>编号</td>
						<td>姓名</td>
						<td>健康情况</td>
						<td>是否去过风险区</td>
						<td>今日体温</td>
						<td>地址</td>
						<td>联系方式</td>
						<td>填表时间</td>
						<td>通过</td>

					</tr>


					<%
						//遍历theKey表取主key
					String sql0 = "select * from theKey where id=2";
					PreparedStatement pstmt0 = con.prepareStatement(sql0);
					ResultSet rs0 = pstmt0.executeQuery();
					if (rs0.next()) {
						mainKey = rs0.getString("mainKey");

					}

					String sql1 = "select state.state,checking.checkDo,state.name,checking.isCheckd,checking.id,state.clock,state.goArea,state.temper,state.addr_cipher,state.phone_cipher,state.infoRan FROM state INNER JOIN checking ON checking.id = state.id order by checking.checkDo";
					PreparedStatement pstmt1 = con.prepareStatement(sql1);
					ResultSet rs1 = pstmt1.executeQuery();
					while (rs1.next()) {
						isCheckd = rs1.getString("isCheckd");

						id = rs1.getInt("id");
						name = rs1.getString("name");
						clock = rs1.getString("clock");
						goArea = rs1.getInt("goArea");
						temper = rs1.getDouble("temper");

						addr_cipher = rs1.getString("addr_cipher");
						phone_cipher = rs1.getString("phone_cipher");

						state = rs1.getString("state");
						checkDo = rs1.getString("checkDo");
						//checkDo = rs.getString("checkDo");
						//System.out.println("---key为："+infoKey);

						//遍历state取随机数生成key2并解密addr和phone
						infoRan = rs1.getString("infoRan");

						theKey = Sm4Util.encryptEcb(mainKey, infoRan);
						//System.out.println("theKey：" + theKey);
						String key2 = theKey.substring(0, 32);
						//System.out.println("key2为：" + key2);
						//System.out.println("addr_cipher为：" + addr_cipher);
						addr = Sm4Util.decryptEcb(key2, addr_cipher);
						phone = Sm4Util.decryptEcb(key2, phone_cipher);
					%>

					<tr>
						<td><%=id%></td>
						<td><%=name%></td>
						<td><%=state%></td>
						<td>
							<%
								if (goArea == 0) {
								out.println("未去过风险区");
							} else {
								out.println("去过风险区");
							}
							%>
						</td>
						<td><%=temper%></td>
						<td><%=addr%></td>
						<td><%=phone%></td>

						<td><%=clock%></td>
						<td>
							<%
								if (checkDo.equals("通过")) {
								out.println("通过");
							} else {
							%>
							<div class="layui-btn-container">

								<button type="button" class="layui-btn layui-btn-sm"
									onclick="document.getElementById('myform0').submit()">
									<form method="post" action="/sm4/administrator/selectdo.do"
										id="myform0">
										<input type="hidden" name="id" value="<%=id%>"> 点击通过
									</form>
								</button>
							</div> <%
 	}
 %>
						</td>
					</tr>

					<%
						}
					pstmt.close();
					con.close();

					System.out.println("------数据库操作成功！");
					} catch (SQLException e) {
					System.out.println("------数据库操作失败！");
					e.printStackTrace();
					}
					%>
					<div style="width: 370px; margin: 0;">
						<button type="button" class="layui-btn layui-btn-fluid">
							<a href="javascript:void(0)" onclick="closeDialog()">点击关闭本窗口</a>
						</button>
					</div>
					</div>

					<div id="fade" class="white_overlay"></div>
</body>

<script type="text/javascript">
	$(function() {})
	function openDialog() {
		document.getElementById('light').style.display = 'block';
		document.getElementById('fade').style.display = 'block'
	}
	function closeDialog() {
		document.getElementById('light').style.display = 'none';
		document.getElementById('fade').style.display = 'none'
	}
</script>

<script src="../js/layui.js" charset="utf-8">
	
</script>
<script src="../js/jquery.min.js">
	
</script>
</html>
