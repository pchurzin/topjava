<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Meal list</title>
    <style>
        .exceeded {background-color: red;}
        .notexceeded {background-color: green;}
    </style>
</head>
<body>
<table>
    <tr>
        <th>Date/Time</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    <c:forEach items="${meals}" var="meal">
    <tr class="${meal.exceed ? "exceeded" : "notexceeded"}">
        <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm" value="${meal.dateTime}" var="parsedDateTime" type="both" />
        <td><fmt:formatDate value="${parsedDateTime}" pattern="dd.MM.yyyy HH:mm"/></td>
        <td><c:out value="${meal.description}" /></td>
        <td><c:out value="${meal.calories}" /></td>
    </tr>
    </c:forEach>
</table>
</body>
</html>