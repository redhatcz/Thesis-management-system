<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <g:if test="${redirect}">
        <meta http-equiv="refresh" content="5;URL='${g.createLink(controller: 'login')}'">
    </g:if>
    <title>${title}</title>
</head>
<body>
    <h3>${header}</h3>
    <p>${content}</p>
</body>
</html>