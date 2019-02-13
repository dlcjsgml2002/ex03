<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	작성자 : ${writer } <br>
	<c:forEach var="file" items="${list }">
		파일이름 : ${file } <br>
		<img alt="" src="${pageContext.request.contextPath }/resources/upload/${file}"><br>
		<br><br>
	</c:forEach>
</body>
</html>