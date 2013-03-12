<%@ page import="com.redhat.theses.Topic" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <title><g:message code="thesis.show.title"/></title>
</head>

<body>
<h2 class="header"><g:fieldValue bean="${thesisInstance?.topic}" field="title"/></h2>
    <g:form class="form-inline" method="post" name="thesis-form">
        <g:hiddenField name="thesis.id" value="${thesisInstance?.id}"/>
        <g:hiddenField name="thesis.version" value="${thesisInstance?.version}"/>
        <g:render template="formEdit"/>
        <div class="control-group">
            <div class="controls">
                <g:actionSubmit class="tms-btn" action="update"
                                value="${message(code: 'default.update.button')}"/>
            </div>
        </div>
    </g:form>
    <div id="file-uploader">
        <h3>File Upload</h3>
        <g:if test="${files}">
        <table class="table">
            <thead>
                <tr>
                    <th><g:message code="file.label"/></th>
                    <th><g:message code="file.type.label"/></th>
                    <th><g:message code="file.uploadDate.label"/></th>
                </tr>
            </thead>
            <g:each in="${files}" var="f">
                <tr>
                    <td>
                        <grid:link mongoId="${f.id}" bucket="thesis"
                                   save="${true}">${f.filename.encodeAsHTML()}</grid:link>
                    </td>
                    <td>
                        ${f.contentType.encodeAsHTML()}
                    </td>
                    <td>
                        <g:formatDate date="${f.uploadDate}" dateStyle="LONG" type="datetime" />
                    </td>
                </tr>
            </g:each>
        </table>
        </g:if>

        <g:else>
        <p><g:message code="file.no.files.uploaded"/></p>
        </g:else>

        <u:uploader topic="thesis" params="${[id: thesisInstance.id]}"/>
    </div>
</body>
</html>
