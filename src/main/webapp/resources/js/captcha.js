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
            success: function (jsonToken, textStatus, xhr) {
                //alert(xhr.status + " " + textStatus);
                document.cookie = "response=" + jsonToken.response;
                document.location.href = "/captcha/result";
            },
            complete: function (xhr, textStatus) {
                if (xhr.status == 403) {
                    alert(xhr.status + "\n" +
                        "Переход запрещен. Вероятные причины:\n" +
                        "- неверно введены символы с картинки;\n" +
                        "- символы с картинки введены не с первой попытки;\n" +
                        "- время введения вами символов с картинки превысило предел;\n" +
                        "- нарушен сценарий переходов в ходе проверки.\n" +
                        "Вы будете возвращены на стартовую страницу.");
                    document.location.href = "/";
                }
                if (xhr.status == 401) {
                    alert(xhr.status + "\n" +
                        "Вы не зарегистрированы.\n" +
                        "Вы будете возвращены на стартовую страницу.\n" +
                        "Пожалуйста, зарегистрируйтесь.");
                    document.location.href = "/";
                }
            }
        });
    })
})