<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Meal form</title>
</head>
<body>
<c:set var="parsedDateTime" value=""/>
<c:if test="${not empty meal}">
    <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm" value="${meal.dateTime}" var="parsedDateTime" type="both"/>
    <fmt:formatDate value="${parsedDateTime}" pattern="yyyy-MM-dd" var="parsedDate"/>
    <fmt:formatDate value="${parsedDateTime}" pattern="HH:mm" var="parsedTime"/>
</c:if>
<form action="meals" method="post">
    <label for="description">Description</label>
    <input type="text" name="description" id="description" value="${meal.description}">
    <label for="date">Date</label><input type="date" name="date" id="date" value="${parsedDate}">
    <label for="time">Time</label><input type="time" name="time" id="time" value="${parsedTime}">
    <label for="calories">Calories</label>
    <input type="number" min="0" name="calories" id="calories" value="${meal.calories}">
    <input type="hidden" name="id" value="${meal.id}">
    <input type="hidden" name="action" value="${ empty meal ? 'create' : 'update'}">
    <input type="submit" value="Send">
</form>
</body>
</html>