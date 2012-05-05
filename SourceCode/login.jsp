<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Login</title>
    
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
  <s:form action="LoginAction" theme="simple" >
  
  <table align="center"> 
  <tr>
  <td align="center">Administer Login</td>
  </tr>
  <tr>
  <td align="center">Username:<s:textfield name="username" ></s:textfield></td>
  </tr>
  <tr>
  <td align="center">Password:&nbsp;&nbsp;&nbsp;<s:password name="password"></s:password></td>
  </tr>
   <tr>
  <td align="center"><s:submit value="login"></s:submit></td>
  </tr> 
   </table>
 </s:form>
  </body>
</html>
