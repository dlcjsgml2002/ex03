<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="out" method="post" enctype="multipart/form-data">
		<input type="text" name="writer" placeholder="작성자 이름"><br>
		<input type="file" name="file">
		<input type="submit" value="전송">
	</form>
</body>
</html>