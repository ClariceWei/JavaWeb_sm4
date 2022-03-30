<%@page pageEncoding="utf-8" contentType="text/html;charset=UTF-8"
	import="java.util.*,java.sql.*"%>
<%@page import="com.sm4.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="robots" content="noindex, nofollow">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="../css/layui.css" media="all">
<!-- 将百度地图API引入，设置好自己的key -->
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=7a6QKaIilZftIMmKGAFLG7QT1GLfIncg">
</script>

<title>check</title>
</head>
<body>

	<%
		request.setCharacterEncoding("UTF-8");
	//String name = (String) request.getAttribute("name");
	//String phone_plain = (String) request.getAttribute("phone_plain");
	String name = (String) session.getAttribute("name");
	String phone_plain = (String) session.getAttribute("phone_plain");
	%>

	<div class="layui-layout layui-layout-admin">
		<div class="layui-header">
			<div class="layui-logo">个人信息签到表</div>
			<!-- 头部区域（可配合layui 已有的水平导航） -->
			<ul class="layui-nav layui-layout-left">

			</ul>
			<ul class="layui-nav layui-layout-right">
				<li class="layui-nav-item"><a href="javascript:;"> <img
						src="../a.png" class="layui-nav-img"> <%=name%>
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
					<li class="layui-nav-item layui-nav-itemed"><a
						href="javascript:;">学生页面</a>
						<dl class="layui-nav-child">
								<dd>
								<a onclick="document.getElementById('myform').submit()">
									<form method="post" action="/sm4/logout.do" id="myform">
										登录页面</form>
								</a>
							</dd>
							<dd>
								<a href="javascript:;">签到页面</a>
							</dd>

						</dl></li>
					<li class="layui-nav-item"><a class="" href="javascript:;">管理员页面</a>
						<dl class="layui-nav-child">
							<dd>
								<a href="javascript:;" title="请以管理员身份登录">登录页面</a>
							</dd>
							<dd>
								<a href="javascript:;" title="请以管理员身份登录">管理页面</a>
							</dd>
							<dd>
								<a href="javascript:;" title="请以管理员登录">审核页面</a>
							</dd>

						</dl></li>

				</ul>
			</div>
		</div>

		<div class="layui-body">
			<!-- 内容主体区域 -->
			<div style="padding: 15px;">


				<div class="main-div">
					<fieldset class="layui-elem-field layui-field-title"
						style="margin-top: 50px;">
						<legend> 个人信息 </legend>
					</fieldset>
					<form class="layui-form layui-form-pane" method="post"
						action="/sm4/user/checkdoJDBC.do">

						<table border="0" width="600" align="center">
							<div class="layui-form-item">
								<label class="layui-form-label"> 姓名 </label>
								<div class="layui-input-block">
									<input type="text" name="name" autocomplete="off"
										placeholder="请输入您的姓名" class="layui-input" value="<%=name%>">
								</div>
							</div>

							<div class="layui-form-item">
								<label class="layui-form-label"> 联系方式 </label>
								<div class="layui-input-block">
									<input type="text" name="phone" autocomplete="off"
										placeholder="请输入您的联系方式" class="layui-input"
										value="<%=phone_plain%>">
								</div>
							</div>

							<div class="layui-form-item">
								<label class="layui-form-label"> 健康状况 </label>
								<div class="layui-input-block">
									<input type="checkbox" name="state" title="健康" value="健康"
										checked /> <input type="checkbox" name="state" title="咳嗽"
										value="咳嗽" /> <input type="checkbox" name="state" title="发烧"
										value="发烧" /> <input type="checkbox" name="state" title="头晕"
										value="头晕" />
								</div>
							</div>

							<div class="layui-form-item">
								<label class="layui-form-label"> 体温 </label>
								<div class="layui-input-block">
									<input type="text" name="temper" autocomplete="off"
										placeholder="请输入您的体温" class="layui-input">
								</div>
							</div>

							<div class="layui-form-item">
								<label class="layui-form-label"> 曾去风险地 </label>
								<div class="layui-input-block">
									<input type="radio" name="goArea" value="0" title="无" checked>
									<input type="radio" name="goArea" value="1" title="有">
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label"> 日期与时间 </label>
								<div class="layui-input-block">
									<input type="text" name="clock" autocomplete="off"
										placeholder="点击显示您填表的时间" class="layui-input"
										onclick='ChkChange(this)'>
								</div>
							</div>


							<div class="layui-form-item">
								<label class="layui-form-label"> 地址 </label>
								<div class="layui-input-inline">
									<input type="text" name='addr' id='sever_add' readonly
										lay-verify="required" placeholder="请输入您的地址" autocomplete="off"
										class="layui-input">
								</div>

								<div class="layui-btn-container">

									<button type="button" class="layui-btn" id='open'>
										点击显示地图获取地址</button>

								</div>


								<!-- 经度 -->
								<input type="hidden" name="lng" id="lng" value="" />
								<!-- 纬度 -->
								<input type="hidden" name="lat" id="lat" value="" />

								<div class="layui-form-item">
									<div class="layui-input-block">
										<button type="submit" class="layui-btn" lay-submit=""
											lay-filter="demo1">立即提交</button>
										<button type="reset" class="layui-btn layui-btn-primary">
											重置</button>
									</div>
								</div>
								</form>
								<div id='allmap'
									style='width: 50%; height: 50%; position: absolute; display: none'></div>

							</div>
							</div>

							<div class="layui-footer">
								<!-- 底部固定区域 -->
								基于国密算法的校园疫情签到系统
							</div>
							</div>

							<script src="./layui/layui.js">
</script>
							<script>
								//JavaScript代码区域
								layui.use('element', function() {
									var element = layui.element;
							
								});
							</script>
</body>
<!--姓名-->
<SCRIPT LANGUAGE="JavaScript">

	function auto_name(name) {
		name.value = "<%=name%>";

	}
</SCRIPT>



<!--时间-->
<SCRIPT LANGUAGE="JavaScript">

	function ChkChange(n) {
		var d = new Date();
		n.value = d.toLocaleString();

	}
</SCRIPT>

<!--位置-->
<script type="text/javascript">
	function validate() {
		var sever_add = document.getElementsByName('sever_add')[0].value;
		if (isNull(sever_add)) {
			alert('请选择地址');
			return false;
		}
		return true;
	}
	//判断是否是空
	function isNull(a) {
		return (a == '' || typeof (a) == 'undefined' || a == null) ? true : false;
	}
	document.getElementById('open').onclick = function() {
		if (document.getElementById('allmap').style.display == 'none') {
			document.getElementById('allmap').style.display = 'block';
		} else {
			document.getElementById('allmap').style.display = 'none';
		}
	}
	var map = new BMap.Map("allmap");
	var geoc = new BMap.Geocoder(); //地址解析对象
	var markersArray = [];
	var geolocation = new BMap.Geolocation();
	var point = new BMap.Point(116.331398, 39.897445);
	map.centerAndZoom(point, 12); // 中心点
	geolocation.getCurrentPosition(function(r) {
		if (this.getStatus() == BMAP_STATUS_SUCCESS) {
			var mk = new BMap.Marker(r.point);
			map.addOverlay(mk);
			map.panTo(r.point);
			map.enableScrollWheelZoom(true);
		} else {
			alert('failed' + this.getStatus());
		}
	}, {
		enableHighAccuracy : true
	})
	map.addEventListener("click", showInfo);
	//清除标识
	function clearOverlays() {
		if (markersArray) {
			for (i in markersArray) {
				map.removeOverlay(markersArray[i])
			}
		}
	}
	//地图上标注
	function addMarker(point) {
		var marker = new BMap.Marker(point);
		markersArray.push(marker);
		clearOverlays();
		map.addOverlay(marker);
	}
	//点击地图时间处理
	function showInfo(e) {
		document.getElementById('lng').value = e.point.lng;
		document.getElementById('lat').value = e.point.lat;
		geoc.getLocation(e.point, function(rs) {
			var addComp = rs.addressComponents;
			var address = addComp.province + addComp.city + addComp.district
			+ addComp.street + addComp.streetNumber;
			if (confirm("确定要地址是" + address + "?")) {
				document.getElementById('allmap').style.display = 'none';
				document.getElementById('sever_add').value = address;
			}
		});
		addMarker(e.point);
	}
</script>

<script src="../js/layui.js" charset="utf-8">
</script>
<script src="../js/jquery.min.js">
</script>
</html>