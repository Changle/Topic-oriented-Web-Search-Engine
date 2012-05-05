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
			var val= document.forms[0].fieldname.value;
			
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
		<center>
			<img src="images/searchba.GIF" width=380 height=150 usemap="#mp" id=lg>
			<br>
			<br>
			<br>
			<br>
			

			<html:form action="AdvanceSearchAction" theme="simple" >
			<table>
				<tr>
				<td><font><b>Type</b></font></td>
				<td>
				<select name="qtype">
                    <option value="term" selected="selected">accurate
                	<option value="fuzz">fuzzy
                	<option value="pref">prefix
                </select>
				<html:textfield  name="fieldname" size="42" maxlength="100" required="true"></html:textfield> 
	            <input type="button" value="Search"  onclick="check()"/>
				</td>
				</tr>
				<tr>
				</tr>
				<tr>
				<td><font><b>Search results number per paage</b></font></td>
				<td>
				<select name="pagetype">
                    <option value="10" selected="selected">10pages
                	<option value="20">20pages
                	<option value="50">50pages
                </select>
				</td>
				</tr>
				<tr>
				</tr>
				<tr>
                <td><font><b>Search Result Setting</b></font></td>
				<td>
				<select name="totalpage">
                    <option value="100" selected="selected">Top 100 hits
                	<option value="200">Top 200 hits
                	<option value="500">Top 500 hits

                </select>
				</td>

				</tr>
				<tr>
				</tr>				
				<tr>
				<td><font><b>Serach File Type</b></font></td>
				<td>
				<select name="filetype">
				    <option value="webhtml" selected="selected">Html Document
                    <option value="pdf" >PDF dcoument
                	<option value="doc">Windows doc document
                	<option value="all">All types
                </select>
				</td>
				</tr>
			</table>
			    							
			
      
			</html:form>
			
	
			
			
			<p id=km>

			</p>
			<p style="height: 60px">
			<table cellpadding=0 cellspacing=0 id=lk>
				<tr>
					<td></td>
				</tr>
			</table>

			<p style="height: 30px">
				<a
					
					href="#"><br>
				</a>
			</p>
			<p style="height: 14px">
			</p>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>

			<p id=b>
				<span>Copyright &copy; Changle Wang，Yifei Sun&nbsp; All Rights Reserved</span>
			</p>

		</center>
	</body>
</html>
