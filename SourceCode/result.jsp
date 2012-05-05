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
		<TITLE>Wanderluster Web Search Engine</TITLE>
		<!--STATUS OK-->
		<META http-equiv=content-type content=text/html;charset=gb2312>
		<link href="css/result.css" rel="stylesheet" type="text/css">



	</HEAD>
	<BODY>

		<TABLE height=54 cellSpacing=0 cellPadding=0 width="100%" align=center>
			<TBODY>
				<TR vAlign=center>
					<TD style="PADDING-LEFT: 8px; WIDTH: 137px" vAlign=top noWrap
						width="100%">
						<a href="index.jsp"><IMG height="50" alt="Back Home" src="images/searchba.GIF" width=150
							border=0></a>
					</TD>
					<TD>
						&nbsp;&nbsp;&nbsp;
					</TD>
					<TD vAlign=top width="100%">
						<DIV class=Tit>
						</DIV>
						<TABLE cellSpacing=0 cellPadding=0>
							<TBODY>
								<TR>
									<TD vAlign=top noWrap>



										<form action="SearchAction.action">
											<html:textfield name="fieldname" size="42"
												value="%{#request.sk}" maxlength="100" />
											<html:submit value="Search" />

										</form>



									</TD>
									<TD vAlign=center noWrap>
										&nbsp;&nbsp;
										<A href="advancesearch.jsp"></A>
									</TD>
								</TR>
							</TBODY>
						</TABLE>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
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
								<A
									href="${eachdoc.originalFileName}"
									target=_blank>${eachdoc.filename} </A>&lt;${eachdoc.type}&gt; 
								<BR>
								${eachdoc.contents}
								<BR>
								${eachdoc.originalFileName}
								<BR>

							</TD>
						</TR>
					</TBODY>
				</TABLE>
				
			</c:forEach>
			
			<br/>
			<br/>




			<c:choose>
				<c:when test="${not empty requestScope.pageNumBean.upPageNum}">
					<a href="${pageUrl}${requestScope.pageNumBean.upPageNum}&sk=${sk1}">Previous</a>
				</c:when>
				<c:otherwise>  
 			       Previous   
   				</c:otherwise>
			</c:choose>

			<c:forEach items="${requestScope.pageNumBean.pages}" var="item">
				<c:choose>
					<c:when test="${item == requestScope.pageNumBean.currentNum}">
						<a href="${pageUrl}${item}&sk=${sk1}">${item}</a>
					</c:when>
					<c:otherwise>
						<a href="${pageUrl}${item}&sk=${sk1}">[${item}]</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>

			<c:choose>
				<c:when test="${not empty requestScope.pageNumBean.downPageNum}">
					<a href="${pageUrl}${requestScope.pageNumBean.downPageNum}&sk=${sk1}">Next</a>
				</c:when>
				<c:otherwise>  
    			    Next   
    			</c:otherwise>
			</c:choose>



			<DIV
				style="CLEAR: both; WIDTH: 100%; HEIGHT: 60px; BACKGROUND-COLOR: #eff2fa">

				<TABLE style="MARGIN-LEFT: 18px; HEIGHT: 60px" cellSpacing=0
					cellPadding=0>
					<html:form action="SearchAction" theme="simple">

						<TBODY>
							<TR vAlign=center>
								<TD noWrap>


									<html:textfield name="fieldname" size="42"
										value="%{#request.sk}" maxlength="100" />
									<html:submit value="搜吧" />

									&nbsp;&nbsp;&nbsp;
								</TD>
								<TD noWrap>

								</TD>
							</TR>
						</TBODY>
					</html:form>
				</TABLE>
				<DIV id=ft>
					<span>Copyright &copy;Changle Wang，Yifei Sun&nbsp; All Rights Reserved</span>
				</DIV>
				<IMG style="DISPLAY: none" src="">
	</BODY>
</HTML>
