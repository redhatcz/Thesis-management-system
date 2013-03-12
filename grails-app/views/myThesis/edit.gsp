<%@ page import="com.redhat.theses.Topic" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <title><g:message code="thesis.edit.title" /></title>
</head>

<body>
<h1 class="header"><g:message code="thesis.edit.title"/></h1>
<g:form class="form-horizontal" method="post" name="thesis-form">
    <g:hiddenField name="thesis.id" value="${thesisInstance?.id}"/>
    <g:hiddenField name="thesis.version" value="${thesisInstance?.version}"/>
    <g:render template="formEdit"/>
    <div class="control-group">
        <div class="controls">
            <g:actionSubmit class="btn btn-primary"
                            action="update"
                            value="${message(code: 'default.update.button')}"/>
        </div>
    </div>
</g:form>
</body>
</html>
