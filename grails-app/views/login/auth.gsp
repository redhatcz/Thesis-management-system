<html>
<head>
	<meta name='layout' content='loginLayout'/>
	<title><g:message code="springSecurity.login.title"/></title>
</head>
<body>
    <div class="login-logo">
        <img src="${resource(dir: 'images', file: 'redhat-b.png')}">
    </div>
    <div class="login">
        <form action='${postUrl}' method='POST' id='loginForm' class='form-vertical'>
            <g:render template="/layouts/messages" />
            <g:render template="form" />
            <p class="bottom">
                <g:link class="link pull-left" uri="/">&larr; Back</g:link>
                <a class="link pull-right" href="#">Lost your password?</a>
            </p>
        </form>
    </div>
</body>
</html>
