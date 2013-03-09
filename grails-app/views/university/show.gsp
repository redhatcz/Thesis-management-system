<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="profile">
    <g:set var="entityName" value="${message(code: 'university.label', default: 'University')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
    <div class="span4 sidebar">
        <div class="panel left">
            <div class="avatar">
                %{--University logo will be here soon!--}%
            </div>
            <h4>University Information</h4>
            <div class="panel-content">
                <dl>
                    <g:if test="${universityInstance?.name}">
                    <dt><g:message code="university.name.label" default="Name" /></dt>
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
                    <li class="active"><a href="#">Theses</a></li>
                    <li><a href="#">Activity</a></li>
                    <div class="controls pull-right">
                        <g:hiddenField name="university.id"
                                       value="${universityInstance?.id}" />
                        <g:link class="tms-btn tms-warning"
                                action="edit"
                                id="${universityInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                        <g:actionSubmit class="tms-btn tms-danger"
                                        action="delete"
                                        value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                        onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                    </div>
                </ul>
            </div>
        </g:form>
    </div>
</body>
</html>
