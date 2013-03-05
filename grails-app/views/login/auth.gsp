<html>
<head>
	<meta name='layout' content='loginLayout'/>
	<title><g:message code="springSecurity.login.title"/></title>
</head>
<body>
    <h1 class="login-title">Theses Management System</h1>
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
