<!DOCTYPE html>
<html>
<head>
    <title><g:if env="development">Grails Runtime Exception</g:if><g:else>500 - Error on the server</g:else></title>
    <meta name="layout" content="main">
    <g:if env="development"><link rel="stylesheet" href="${resource(dir: 'css', file: 'errors.css')}" type="text/css"></g:if>
</head>
<body>
    <div class="span12">
        <g:if env="development">
            <g:renderException exception="${exception}" />
        </g:if>
        <g:else>
            <div class="alert alert-error">
                Error 500 - an error on the server has occurred.
            </div>
        </g:else>
    </div>
</body>
</html>
