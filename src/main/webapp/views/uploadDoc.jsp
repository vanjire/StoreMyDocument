<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="/user/upload" method="post" enctype="multipart/form-data">

    <input type="text" name="name" placeholder="Document name">

    <input type="file" name="file">

    <button type="submit">Upload</button>

</form>
</body>
</html>