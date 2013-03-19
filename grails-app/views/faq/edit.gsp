<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <title><g:message code="faq.create.title"/></title>
</head>

<body>
<h2 class="header">
    <g:message code="faq.edit.header"/>
    <small class="pull-right">
        <g:link controller="faq">
            <i class="icon-double-angle-left"></i>
            ${message(code: 'default.back.button')}
        </g:link>
    </small>
</h2>
<g:form class="form-inline" action="update">
    <g:hiddenField name="faq.id" value="${faqInstance?.id}"/>
    <g:hiddenField name="faq.version" value="${faqInstance?.version}"/>
    <g:render template="formEdit"/>
    <div class="control-group">
        <div class="controls">
            <g:submitButton name="edit"
                            class="tms-btn btn-large"
                            value="${message(code: 'default.update.button')}"/>
        </div>
    </div>
</g:form>
</body>
</html>
