<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	작성자 : ${writer } <br>
	파일이름 : ${file } <br>
	<img alt="" src="displayFile?filename=${file }">
</body>
</html>