<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://www.lfm.cn/c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<base href="<%=basePath%>">
<title>CRM管理系统</title>
<jsp:include page="/pages/plugins/import_file.jsp" />
<link href="/css/select.css" rel="stylesheet" type="text/css" />
<link href="/css/<style></style>.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/js/jquery.idTabs.min.js"></script>
<script type="text/javascript" src="/js/select-ui.min.js"></script>
<script type="text/javascript" src="/js/pages/back/role/role_list.js"></script>
</head>
<body>
<body class="sarchbody">

	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="main.html" target="_top">首页</a></li>
			<li><a href="#">角色列表</a></li>
		</ul>
	</div>

	<div class="formbody">
		<div id="usual1" class="usual">
			<div class="formtitle">
				<span>角色列表</span>
			</div>
			<table class="tablelist">
				<thead>
					<tr>
						<th width="4%"><input type="checkbox" id="selAll"/></th>
						<th width="33%">编号</th>
						<th width="33%">名称</th>
						<th width="30%"colspan="2">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${allRoles!= null}">
						<c:forEach items="${allRoles}" var="role">
							<tr>
								<td><input type="checkbox" id="rid" name="rid" value="${role.rid}"/></td>
								<td>${role.rid}</td>
								<td>${role.title}</td>
                                <c:if test="${allActions['34']!=null}">
                                    <td><a href="<%=basePath%>${allActions['34'].url}?rid=${role.rid}" class="tablelink">查看详情</a></td>
                                </c:if>
                                <c:if test="${allActions['21']!=null}">
                                    <td><a href="<%=basePath%>${allActions['21'].url}?rid=${role.rid}" class="tablelink">角色修改</a></td>
                                </c:if>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
            <c:if test="${allActions['22']!=null}">
                <div class="buttonform1">
                    <li class="delbtn"><label>&nbsp;</label>
                        <input id="delClient" type="button" class="deltn" value="删除角色" />
                    </li>
                </div>
            </c:if>
		</div>
	</div>


</body>
</html>
