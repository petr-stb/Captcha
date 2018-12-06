<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <title>Стартовая страница</title>
    <c:url value="/resources/css/style.css" var="cssUrl"/>
    <link rel="stylesheet" type="text/css" href="${cssUrl}"/>
    <script
            src="https://code.jquery.com/jquery-3.3.1.js"
            integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60="
            crossorigin="anonymous"></script>
</head>
<body>
<div class=basis>
    <h1>
        Стартовая страница
        <hr/>
    </h1>
    Для продолжения работы, пожалуйста, зарегистрируйтесь.<br/>
    Регистрация пройдет автоматически после нажатия на кнопку.<br/><br/>
    <div class="button">
        <input type="button" value="Зарегистрироваться" id="register"/>
    </div>
</div>
<script src="/resources/js/start.js"></script>
</body>
</html>
