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
                    alert(xhr.status + "\n" +
                        "Вы не зарегистрированы.\n" +
                        "Вы будете возвращены на стартовую страницу.\n" +
                        "Пожалуйста, зарегистрируйтесь.");
                    document.location.href = "/";
                }
            }
        });

    })
});