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

    <h3>Abstract</h3>
    <g:if test="${thesisInstance?.thesisAbstract}">
        <g:fieldValue field="thesisAbstract" bean="${thesisInstance}"/>
    </g:if>
    <g:else>
        <p>Thesis abstract is missing, please provide a brief description of your thesis.</p>
    </g:else>
    <g:link class="tms-btn tms-warning"
            controller="myThesis"
            action="edit"
            id="${thesisInstance?.id}">
        <g:message code="thesis.asignee.edit" default="Update Abstract"/>
    </g:link>

    <h3>Related files</h3>
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
                        <grid:link mongoId="${f.id}" bucket="thesis"
                                   save="${true}">${f.filename.encodeAsHTML()}</grid:link>
                    </td>
                    <td>${f.contentType.encodeAsHTML()}</td>
                    <td>
                        <g:formatDate date="${f.uploadDate}" dateStyle="LONG" type="datetime" />
                    </td>
                </tr>
            </g:each>
        </table>
    </g:if>
    <g:else>
        <p>
            No files has been uploaded.
        </p>
    </g:else>

    <u:uploader topic="thesis" params="${[id: thesisInstance.id]}"/>
</body>
</html>
