<%@ page import="com.redhat.theses.Topic" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <g:set var="entityName" value="${message(code: 'thesis.label', default: 'Thesis')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>
<body>
    <h2 class="header"><g:fieldValue bean="${thesisInstance?.topic}" field="title"/></h2>
    <g:if test="${files}">
        <table class="table">
            <thead>
                <tr>
                    <th>File</th>
                    <th>Type</th>
                    <th>Uploaded</th>
                </tr>
            </thead>
            <g:each in="${files}" var="f">
                <tr>
                    <td>
                        <grid:link mongoId="${f.id}" bucket="thesis" save="${true}">${f.filename.encodeAsHTML()}</grid:link>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
            </g:each>
        </table>
    </g:if>

    <u:uploader topic="thesis" params="${[id: thesisInstance.id]}"/>
</body>
</html>
