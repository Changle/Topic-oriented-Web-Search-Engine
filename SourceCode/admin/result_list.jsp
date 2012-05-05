<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="html" uri="/struts-tags"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<TITLE>Wanderluster Search Engine</TITLE>
		<!--STATUS OK-->
		<META http-equiv=content-type content=text/html;charset=gb2312>
		<link href="../css/result.css" rel="stylesheet" type="text/css">



	</HEAD>
	<BODY>
	<html:form action="DoDeleteAction" theme="simple">

		<TABLE class=bi cellSpacing=0 cellPadding=0 width="100%" align=center
			border=0>
			<TBODY>
				<TR>
					<TD noWrap align="left">
						&nbsp;&nbsp;About
						<html:property value="%{#session.rsize}" />
						Results&nbsp;&nbsp;&nbsp;&nbsp;
					</TD>
				</TR>
			</TBODY>
		</TABLE>


			<BR>
			<c:forEach items="${rlist}" var="eachdoc">
				<TABLE cellSpacing=0 cellPadding=0 border=0>
					<TBODY>
						<TR>
							<TD class="f EC_PP">
							     
								<input type="checkbox" name="delchk" Value="${eachdoc.id}"/>${eachdoc.filename}<br>
								<BR>

							</TD>
						</TR>
					</TBODY>
				</TABLE>
			</c:forEach>




			<c:choose>
				<c:when test="${not empty requestScope.pageNumBean.upPageNum}">
					<a href="${pageDelUrl}${requestScope.pageNumBean.upPageNum}&sk=${sk1}">Previous</a>
				</c:when>
				<c:otherwise>  
 			       Previous   
   				</c:otherwise>
			</c:choose>

			<c:forEach items="${requestScope.pageNumBean.pages}" var="item">
				<c:choose>
					<c:when test="${item == requestScope.pageNumBean.currentNum}">
						<a href="${pageDelUrl}${item}&sk=${sk1}">${item}</a>
					</c:when>
					<c:otherwise>
						<a href="${pageDelUrl}${item}&sk=${sk1}">[${item}]</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>

			<c:choose>
				<c:when test="${not empty requestScope.pageNumBean.downPageNum}">
					<a href="${pageDelUrl}${requestScope.pageNumBean.downPageNum}&sk=${sk1}">Next</a>
				</c:when>
				<c:otherwise>  
    			    Next   
    			</c:otherwise>
			</c:choose>



			<DIV
				style="CLEAR: both; WIDTH: 100%; HEIGHT: 60px; BACKGROUND-COLOR: #eff2fa">

				<TABLE style="MARGIN-LEFT: 18px; HEIGHT: 60px" cellSpacing=0
					cellPadding=0>
		

						<TBODY>
							<TR vAlign=center>
								<TD noWrap>

                                    <html:hidden name="fieldname" value="%{#request.sk}"></html:hidden>

								    <html:submit value="Delete"/>
									&nbsp;&nbsp;&nbsp;
								</TD>
								<TD noWrap>

								</TD>
							</TR>
						</TBODY>
					
				</TABLE>
				<DIV id=ft>
					<span>Copyright &copy;Changle Wang，Yifei Sun&nbsp; All Rights Reserved</span>
				</DIV>
				<IMG style="DISPLAY: none" src="">
				</html:form>
	</BODY>
</HTML>
