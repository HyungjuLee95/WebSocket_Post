<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	chatmain입니다  
</h1>

<table>
<c:forEach items="${vo2}" var="vo">
<tr>
<td> ${vo.roomid}</td>
</tr>
</c:forEach>
</table>
</body>
<a href="login.do">로그인페이지</a>
</html>
