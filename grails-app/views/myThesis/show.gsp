<%@ page import="com.redhat.theses.Topic" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <title><g:message code="thesis.show.title"/></title>
</head>

<body>
<h2 class="header"><g:fieldValue bean="${thesisInstance?.topic}" field="title"/></h2>

    <h3><g:message code="thesis.thesisAbstract.label"/></h3>
    <g:if test="${thesisInstance?.thesisAbstract}">
        <g:fieldValue field="thesisAbstract" bean="${thesisInstance}"/>
    </g:if>
    <g:else>
        <p><g:message code="thesis.thesisAbstract.missing"/></p>
    </g:else>
    <g:link class="tms-btn tms-warning"
            controller="myThesis"
            action="edit"
            id="${thesisInstance?.id}">
        <g:message code="thesis.thesisAbstract.edit.button"/>
    </g:link>

    <h3>Related files</h3>
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
                    <td>${f.contentType.encodeAsHTML()}</td>
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
</body>
</html>
