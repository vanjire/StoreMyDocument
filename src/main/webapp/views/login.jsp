<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>

<body>

    <h2>Login</h2>

    <%
        String error = request.getParameter("error");

        if (error != null) {
    %>

        <p style="color: red;">
            Invalid username or password
        </p>

    <%
        }
    %>


    <form action="${pageContext.request.contextPath}/store/login"
          method="post">

        <label>Username:</label>
        <input type="text"
               name="username"
               required>

        <br><br>

        <label>Password:</label>
        <input type="password"
               name="password"
               required>

        <br><br>

        <button type="submit">
            Login
        </button>

    </form>


    <p>
        Don't have an account?

        <a href="${pageContext.request.contextPath}/store/register">
            Register
        </a>
    </p>

</body>

</html>