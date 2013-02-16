<%@ page import="com.redhat.theses.Tag" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'tag.label', default: 'Tag')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
    <div class="span8">
        <h1 class="header">
            <g:fieldValue bean="${tagInstance}" field="title"/>
        </h1>

        <dl class="dl-horizontal">
            <g:if test="${tagInstance?.parent}">
                <dt><g:message code="tag.parent.label" default="Parent" /></dt>
                <dd><g:link controller="tag" action="show" id="${tagInstance?.parent?.id}">
                    ${tagInstance?.parent?.encodeAsHTML()}
                </g:link></dd>
            </g:if>

            <g:if test="${tagInstance?.subTags}">
                <dt><g:message code="tag.subTags.label" default="Sub Tags" /></dt>
                <g:each in="${tagInstance.subTags}" var="s">
                    <dd><g:link controller="tag" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></dd>
                </g:each>
            </g:if>
        </dl>
    </div>

    <div class="span4">
        <div class="panel right">
            <h4>Tag Management</h4>
            <div class="panel-content">
                <g:form>
                    <fieldset class="buttons">
                        <g:hiddenField name="tag.id" value="${tagInstance?.id}" />
                        <g:link class="tms-btn tms-warning" action="edit" id="${tagInstance?.id}">
                            <g:message code="default.button.edit.label" default="Edit" />
                        </g:link>
                        <g:actionSubmit class="tms-btn tms-danger" action="delete"
                                        value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                        onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                    </fieldset>
                </g:form>
            </div>
        </div>
    </div>
</body>
</html>
