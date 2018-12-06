<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <title>Вы зарегистрированы</title>
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
        Вы зарегистрированы
        <hr/>
    </h1>
    Вы зарегистрировались и получили ключи регистрации.<br/>
    Ваш публичный ключ:<br/><br/>
    <div id="publicKey"><br/></div>
    <br/>
    Для доступа на скрытую страницу необходимо пройти проверку.<br/><br/>
    <div class="button">
        <input type="button" value="Пройти проверку и получить доступ" id="check"/>
    </div>
</div>
<script src="/resources/js/client.js"></script>
</body>
</html>
