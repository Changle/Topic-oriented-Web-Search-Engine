<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Upload</title>
    
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
  <center>
    <img src="images/searchba.GIF" width=380 height=150 usemap="#mp" id=lg alt="Back Home">
  <table >
  <tr>
  <td>
    <s:form action="fileUpload" enctype="multipart/form-data" method="post">
    <s:file name="file" label="Choose file you want to upload"/>
    <s:submit value="Upload"/>
    </s:form>
    </td>
   </tr>
  </table>
  </center>
  </body>
</html>
