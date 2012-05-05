<%@ page language="java" import="java.util.*, com.wanderluster.db.Admin" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Admin admin=(Admin)session.getAttribute("user");
String username=admin.getUsername();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Login Sucessfully</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
   Welcome！<%=username %><br>
   <a href="./admin/fileindex.jsp">Increase Index</a>&nbsp;|&nbsp; 
   <a href="./admin/delSearch.jsp">Delete Index</a>&nbsp;|&nbsp; 
  </body>
</html>
