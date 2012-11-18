<html>
<head>
	<meta name='layout' content='light'/>
	<title><g:message code="springSecurity.login.title"/></title>
</head>

<body>
    <h1 class="header">
        <g:message code="default.login.label" default="Login" />
    </h1>
    <div class="offset2">
        <form action='${postUrl}' method='POST' id='loginForm' class='form-horizontal'>
            <div class="control-group">
                <label class="control-label" for='email'>
                    <g:message code="springSecurity.login.email.label" default="Email"/>
                </label>
                <div class="controls">
                    <input type='text' name='j_username' id='username'
                           placeholder="${message(code: 'springSecurity.login.email.label', default: 'Email')}" />
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for='password'>
                    <g:message code="springSecurity.login.password.label"/>
                </label>
                <div class="controls">
                    <input type='password' name='j_password' id='password'
                           placeholder="${message(code: 'springSecurity.login.password.label', default: 'Password')}" />
                </div>
            </div>

            <div class="control-group">
                <div class="controls">
                    <label class="checkbox" for='remember_me'>
                        <input type='checkbox' name='${rememberMeParameter}'
                               id='remember_me' <g:if test='${hasCookie}'>checked='checked'</g:if>/>
                        <g:message code="springSecurity.login.remember.me.label"/>
                    </label>
                </div>
            </div>

            <div class="control-group">
                <div class="controls">
                    <input type='submit' id="submit" class="btn btn-primary"
                           value='${message(code: "springSecurity.login.button")}'/>
                </div>
            </div>
        </form>
    </div>
</body>
</html>
