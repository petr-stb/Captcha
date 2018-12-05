<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <title>Проверка</title>
    <script
            src="https://code.jquery.com/jquery-3.3.1.js"
            integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60="
            crossorigin="anonymous"></script>
</head>
<body>
<div>
    <h1>Проверка</h1>
</div>
   <div>
       Введите текст с картинки и нажмите "Отправить"<br/><br/>
   </div>
   <div>
       <img name = 'img'/><br/><br/>
   </div>
   <div>
       <form name="form" action="">
           <input type="text" name="answer" id="answerString"/>
           <input type="submit" value="Отправить" id="answerButton"/>
       </form>
   </div>
</body>
<script type="text/javascript">
    $(document).ready(function () {
        var pub = "";
        var req = "";
        var r = document.cookie.match("(^|;) ?" + "public" + "=([^;]*)(;|$)");
        if (r) pub = r[2];
        r = document.cookie.match("(^|;) ?" + "request" + "=([^;]*)(;|$)");
        if (r) req = r[2];
        document.img.src = "/captcha/image?public=" + pub + "&request=" + req;
        $("#answerButton").click(function () {

            $.ajax({
                url: "captcha/solve",
                type: "POST",
                data: {"public": pub, "request": req, "answer": document.getElementById('answerString').value},
                success: function(jsonToken, textStatus, xhr){
                    //alert(xhr.status + " " + textStatus);
                    document.cookie = "response=" + jsonToken.response;
                    document.location.href = "/captcha/result";
                },
                complete: function(xhr, textStatus) {
                    if(xhr.status == 403){
                        alert(xhr.status + " " + textStatus + "\n" +
                        "Доступ запрещен. Вероятные причины:\n" +
                        "- неверно введено слово с картинки;\n" +
                        "- слово с картинки введено не с первой попытки;\n" +
                        "- время введения вами слова с картинки превысило предел;\n" +
                        "- нарушен сценарий переходов в ходе проверки.");
                        document.location.href = "/";
                    }
                    if(xhr.status == 401){
                        alert(xhr.status + " " + textStatus + "\n" +
                            "Доступ запрещен. Вы не зарегистрированы.");
                        document.location.href = "/";
                    }
                }
            });
        })
    })
</script>

</html>
