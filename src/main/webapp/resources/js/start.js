$(document).ready(function () {
    $("#register").click(function () {
        $.ajax({
            url: "client/register",
            type: "POST",
            data: {},
            success: function (jsonKeys) {
                document.cookie = "secret=" + jsonKeys.secret;
                document.cookie = "public=" + jsonKeys.public;
                document.location.href = "/client";
            }
        });
    })
})