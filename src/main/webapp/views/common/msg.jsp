<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String msg=(String)request.getAttribute("msg");
	String loc=(String)request.getAttribute("loc");
	String script=(String)request.getAttribute("script");
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메세지페이지</title>
</head>
<body>
	<script>
		alert('<%=msg%>');
		// /05_HelloMVC/
		location.replace("<%=request.getContextPath()%><%=loc%>");
		<%=script!=null?script:""%>
		opener.location.replace("/logout");close();
	</script>
</body>
</html>





