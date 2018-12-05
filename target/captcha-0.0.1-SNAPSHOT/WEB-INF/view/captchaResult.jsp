<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <title>Проверка пройдена</title>
    <script
            src="https://code.jquery.com/jquery-3.3.1.js"
            integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60="
            crossorigin="anonymous"></script>
</head>
<body>
<div>
    <h1>Вы прошли проверку!</h1>
</div>
    Вам предоставлен однократный доступ на закрытую страницу.<br/>
    Нажмите на кнопку для перехода.<br/>
<div>
    <input type="button" value="Посмотреть закрытую страницу" id="open"/>
</div>
</body>
<script type="text/javascript">
    $(document).ready(function(){
        var sec = "";
        var res = "";
        var r = document.cookie.match("(^|;) ?" + "secret" + "=([^;]*)(;|$)");
        if (r) sec = r[2];
        r = document.cookie.match("(^|;) ?" + "response" + "=([^;]*)(;|$)");
        if (r) res = r[2];
        $("#open").click(function () {
            document.location.href = "/captcha/closedPage?secret=" + sec + "&response=" + res;
        })
    });
</script>
</html>
