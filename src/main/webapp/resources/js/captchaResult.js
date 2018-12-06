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