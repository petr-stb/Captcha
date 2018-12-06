<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <title>Проверка</title>
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
        Проверка
        <hr/>
    </h1>
    Введите символы с картинки и нажмите "Отправить"<br/><br/>
    <div class="image">
        <img name='img' alt="Изображение больше не доступно"/>
    </div>
    <br/>
    <div class=button>
        <form name="form" action="">
            <input type="submit" value="Отправить" id="answerButton"/>
            <input type="text" name="answer" id="answerString"/>
        </form>
    </div>
</div>
<script src="/resources/js/captcha.js"></script>
</body>
</html>
