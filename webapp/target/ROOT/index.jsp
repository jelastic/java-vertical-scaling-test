Obj = <%=session.getAttribute("obj")%>
<%
String [] obj = (String [])session.getAttribute("obj");
if (obj == null) {
  obj = new String[1024 * 1024];
  session.setAttribute("obj", obj);
}
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.time.LocalDateTime" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Simple JSP Application</title>
	</head>
	<body>
		<h1>Hello world!</h1>
		<h2>Current time is <%= LocalDateTime.now() %></h2>
	</body>
</html>