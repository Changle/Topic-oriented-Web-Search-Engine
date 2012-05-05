<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="html" uri="/struts-tags"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv=Content-Type content="text/html;charset=gb2312">
<title>Wanderluster Search Engine</title>
<link href="css/wanderluster.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
			function check(){
			var val= document.forms[0].content.value;
			
			if(val==""){
				   return false;
			   }
			   else
			   {
			   document.forms[0].submit();
			   }
			}
		</script>
</head>
<body>

<br>
<br>
<br>
<br>
<br>
<center><a href="index.jsp"><img src="images/searchba.GIF"
	width=380 height=150 usemap="#mp" id=lg></a> <br>
<br>
<br>
<br>


<html:form action="ClusterAction" theme="simple">

	<input type="button" value="Classify" onclick="check()" />
	<br />
	<br />
	<%
		String str= (String) request.getSession().getAttribute(
					"result");
			String label = null;
			
	%>
	<label><%=str%></label>
	</br>
	<br />
	<br />
	<textarea name="content" rows="10" cols="50"></textarea>



</html:form> <label><font size="1.5"><a
	href="http://web.ist.utl.pt/~acardoso/datasets/r8-test-all-terms.txt"
	,target="_blank">Sample Positive Corpus</a>&nbsp<a
	href="http://web.ist.utl.pt/~acardoso/datasets/20ng-test-all-terms.txt"
	,target="_blank">Sample Negative Corpus</a></font></label> <br />
<label><font size="1.5">The classifier is trained base on
20Newsgroup corpus(20 different topics) and Reuters 21578 corpus(Mainly
economics related)</font></label> <br />
<label><font size="1.5">To better verify accuracy of this
classifier, please select corpus from these copura</font></label> <br />

<p id=km></p>
<p style="height: 60px">
<table cellpadding=0 cellspacing=0 id=lk>
	<tr>
		<td></td>
	</tr>
</table>

<p style="height: 30px"><a href="#"><br>
</a></p>
<p style="height: 14px"></p>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>

<p id=b><span>Copyright &copy;Changle Wang，Yifei Sun&nbsp;
All Rights Reserved</span></p>
</center>
</body>
</html>
