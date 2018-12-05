<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <title>Стартовая страница</title>
    <script
            src="https://code.jquery.com/jquery-3.3.1.js"
            integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60="
            crossorigin="anonymous"></script>
</head>
<body>
    <div>
        <h1>Стартовая страница</h1>
    </div>
    <div>
        <div>
            Для продолжения работы, пожалуйста, пройдите регистрацию.<br/><br/>
        </div>
        <div>
            <input type="button" value="Зарегистрироваться" id="register"/>
        </div>
    </div>
</body>

<script type="text/javascript">
    $(document).ready(function () {
        $("#register").click(function () {
            $.ajax({
                url: "client/register",
                type: "POST",
                data: {},
                success: function(jsonKeys){
                    document.cookie = "secret=" + jsonKeys.secret;
                    document.cookie = "public=" + jsonKeys.public;
                    document.location.href = "/client";
                }
            });
        })
    })
</script>
</html>
