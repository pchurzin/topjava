<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meal list</title>
</head>
<body>
<c:forEach items="${meals}" var="meal">
    <c:out value="${meal}" /><br>
</c:forEach>
</body>
</html>