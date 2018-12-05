<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <title>Вы зарегистрированы</title>
    <script
            src="https://code.jquery.com/jquery-3.3.1.js"
            integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60="
            crossorigin="anonymous"></script>
</head>
<body>
<div>
    <h1>Вы зарегистрированы</h1>
</div>
<div>
    <div>
        Вы зарегистрировались и получили ключи регистрации.<br/>
        Ваш публичный ключ:<br/>
        <div id="publicKey">
        </div>
        <br/>
        Для доступа к закрытой информации необходимо пройти проверку.
    </div>
    <div>
        <input type="button" value="Пройти проверку и получить доступ" id="check"/>
    </div>
</div>
</body>

<script type="text/javascript">
    $(document).ready(function(){
        var r = document.cookie.match("(^|;) ?" + "public" + "=([^;]*)(;|$)");
        if (r) $("#publicKey").text(r[2]);
        else $("#publicKey").text("");
        $("#check").click(function () {
            $.ajax({
                url: "captcha/new",
                type: "GET",
                data: {"public": r[2]},
                success: function(jsonCaptcha){
                    document.cookie = "request=" + jsonCaptcha.request;
                    document.cookie = "answer=" + jsonCaptcha.answer;
                    document.location.href = "/captcha";
                },
                complete: function(xhr, textStatus) {
                    if(xhr.status == 401){
                        alert(xhr.status + " " + textStatus + "\n" +
                            "Доступ запрещен. Вы не зарегистрированы.");
                        document.location.href = "/";
                    }
                }
            });

        })
    });
</script>

</html>
