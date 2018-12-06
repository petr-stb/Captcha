<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <title>Проверка пройдена</title>
    <c:url value="/resources/css/style.css" var="cssUrl"/>
    <link rel="stylesheet" type="text/css" href="${cssUrl}"/>
    <script
            src="https://code.jquery.com/jquery-3.3.1.js"
            integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60="
            crossorigin="anonymous"></script>
</head>
<body>
<div class="basis">
    <h1>
        Вы прошли проверку!<hr/>
    </h1>
    Вам предоставлен однократный доступ на cкрытую страницу.<br/>
    Нажмите на кнопку для перехода.<br/><br/>
    <div class="button">
        <input type="button" value="Посмотреть cкрытую страницу" id="open"/>
    </div>
</div>
<script src="/resources/js/captchaResult.js"></script>
</body>
</html>
