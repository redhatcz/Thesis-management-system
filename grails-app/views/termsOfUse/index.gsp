<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta name="layout" content="main">
    <title><g:message code="termsOfUse.title"/></title>
</head>

<body>
<div class="span8 content">
    <h2 class="header">
        <g:message code="termsOfUse.header"/>
    </h2>

    <div class="tms-description">
        <markdown:renderHtml text="${termsOfUse}"/>
    </div>
</div>
</body>
</html>
