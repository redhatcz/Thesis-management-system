<%@ page import="com.redhat.theses.util.Util; com.redhat.theses.Topic" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <title><g:message code="topic.edit.title" /></title>
</head>
<body>
    <h2 class="header">
        <g:message code="topic.edit.header" />
        <small class="pull-right">
            <g:link action="show" id="${topicInstance?.id}">
                <i class="icon-double-angle-left"></i>
                <g:message code="default.back.button" />
            </g:link>
        </small>
    </h2>
    <g:form class="form-inline" method="post" >
        <g:hiddenField name="topic.id" value="${topicInstance?.id}" />
        <g:hiddenField name="topic.version" value="${topicInstance?.version}" />
        <g:render template="form"/>
        <div class="control-group">
            <div class="controls">
                <g:actionSubmit class="tms-btn btn-large"
                                action="update"
                                value="${message(code: 'default.update.button')}" />
            </div>
        </div>
    </g:form>
</body>
</html>
