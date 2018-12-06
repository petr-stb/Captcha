<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <title>Закрытая страница</title>
    <c:url value="/resources/css/style.css" var="cssUrl"/>
    <link rel="stylesheet" type="text/css" href="${cssUrl}"/>
</head>
<body>
<div class="basis">
    <h1>
        Cкрытая страница<hr/>
    </h1>
    Здесь находится cкрытый контент (котик и есть скрытый контент).<br/>
    <img src="/resources/images/сat.jpg" alt="Здесь должен быть котик, но он убежал. Вы точно прошли проверку?"/><br/>
    Вы можете его видеть, так как вы прошли проверку и получили одноразовый токен для доступа.<br/>
    Если вас попросили протестировать приложение, то сообщите тому, кто попросил, о котике.<br/><br/>
    Для повторного доступа нужно повторно пройти проверку.<br/>
    Обновите страницу (F5) для повторения сценария.<br/><br/>
</div>
</body>
</html>
