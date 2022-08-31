<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="css/styles.css">
    <jsp:useBean id="meals" type="ru.javawebinar.topjava.model.MealTo" scope="request"/>
    <title>Edit meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit meal</h2>
<form method="post" action="meals">
    <dl>
        <dt>DateTime:</dt>
        <dd><input type="datetime-local" placeholder="ГГГГ-ММ-ДД ЧЧ:мм" name="dateTime" value="${meals.dateTime}"></dd>
    </dl>
    <dl>
        <dt>Description:</dt>
        <dd><input type="text" name="description" size=50 placeholder="Описание" value="${meals.description}"></dd>
    </dl>
    <dl>
        <dt>Calories:</dt>
        <dd><input type="number" name="calories" size=30 placeholder="Количество калорий" value="${meals.calories}"></dd>
    </dl>
    <hr>
    <button type="submit">Save</button>
    <button type="button" onclick="window.history.back()">Cancel</button>
</form>
</body>
</html>
