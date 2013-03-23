<html>
<head>
    <meta name='layout' content='loginLayout'/>
    <title><g:message code="security.lost.password.title"/></title>
</head>
<body>
<div class="login-logo">
    <img src="${resource(dir: 'images', file: 'redhat-b.png')}">
</div>
<div class="login">
    <g:form action="lostPasswordConfirm" class='form-vertical'>
        <g:render template="/layouts/messages" />
        <div class="alert alert-info recovery">
            <g:message code="security.lost.password.verify.info"/>
        </div>
        <div class="control-group">
            <label class="control-label" for='emailCommand.email'>
                <g:message code="security.login.email.label" default="Email"/>
            </label>
            <div class="controls">
                <g:textField class="input-block-level wide" id="email"
                             name="emailCommand.email"
                             placeholder="${message(code: 'user.email.label')}"
                             value="${passwordCommand?.email}"/>
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <g:submitButton name="validate-email" class="tms-btn pull-right"
                                value='${message(code: "security.verify.email.button")}'/>
            </div>
        </div>
        <p class="bottom-lost">
            <g:link class="link pull-left" controller="login">&larr; <g:message code="default.back.button"/></g:link>
        </p>
    </g:form>
</div>
</body>
</html>