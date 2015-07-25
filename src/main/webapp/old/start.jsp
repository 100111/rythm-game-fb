<%@page import="util.GenerateAuthLink"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%= "Hello"%>
<% 
	String url  = GenerateAuthLink.generate();
	String tokenUrl =  GenerateAuthLink.generateTokenUrl();
%>
<script type="text/javascript">
	function autho() {
		top.location.href  = "<%= tokenUrl%>";
	}
</script>
<br/>
<a href="<%= url%>">Click to authorize</a>
<br/>
<a onclick="autho();">Click see token</a>
<br/>
<br/>
<%= url%>
<br />
<%= tokenUrl%>
</body>
</html>
