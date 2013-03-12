<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="profile">
    <title><g:message code="university.show.title" /></title>
</head>
<body>
    <div class="span4 sidebar">
        <div class="panel left">
            <div class="avatar">
                %{--University logo will be here soon!--}%
            </div>
            <h4><g:message code="university.information.label"/></h4>
            <div class="panel-content">
                <dl>
                    <g:if test="${universityInstance?.name}">
                    <dt>${message(code: 'university.name.label').toString().toLowerCase()}</dt>
                    <dd><g:fieldValue bean="${universityInstance}" field="name"/></dd>
                    </g:if>
                </dl>
            </div>
        </div>
    </div>
    <div class="span8 content">
        <g:form>
            <div class="control-group">
                <ul class="nav nav-tabs">
                    <div class="controls pull-right">
                        <g:hiddenField name="university.id"
                                       value="${universityInstance?.id}" />
                        <g:link class="tms-btn tms-warning"
                                action="edit"
                                id="${universityInstance?.id}"><g:message code="default.edit.button"/></g:link>
                        <g:actionSubmit class="tms-btn tms-danger"
                                        action="delete"
                                        value="${message(code: 'default.delete.button')}"
                                        onclick="return confirm('${message(code: 'default.delete.confirm.message')}');" />
                    </div>
                </ul>
            </div>
        </g:form>
    </div>
</body>
</html>
