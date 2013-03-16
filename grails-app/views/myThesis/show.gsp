<%@ page import="com.redhat.theses.Topic" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <title><g:message code="thesis.show.title" args="[thesisInstance.title]"/></title>
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
    <g:set var="show" value="${files || false}"/>

    <p id="list-info" style="${show ? 'display: none' : '' }"><g:message code="file.no.files.uploaded"/></p>

    <table class="table" style="${!show ? 'display: none' : '' }" id="file-list">
        <thead>
        <tr>
            <th><g:message code="file.label"/></th>
            <th><g:message code="file.type.label"/></th>
            <th><g:message code="file.uploadDate.label"/></th>
            <th><g:message code="file.action.label"/></th>
        </tr>
        </thead>
        <g:each in="${files}" var="f">
            <tr data-file="${f.id}"r>
                <td>
                    <grid:link mongoId="${f.id}" bucket="thesis"
                               save="${true}">${f.filename.encodeAsHTML()}</grid:link>
                </td>
                <td>
                    ${f.contentType.encodeAsHTML()}
                </td>
                <td>
                    <g:formatDate date="${f.uploadDate}" dateStyle="LONG" type="datetime"/>
                </td>
                <td>
                    <u:deleteLink topic="thesis"
                                  id="${f.id}"
                                  confirm="${g.message(code: 'file.delete.confirm.message', args: [f.filename])}"
                                  onSuccess="removeFromFileList('file-list', 'list-info', data)"
                                  params="${[thesisId: thesisInstance.id]}"><i class="icon-trash"></i></u:deleteLink>
                </td>
            </tr>
        </g:each>
        <tr class="clone-me" style="display: none">
            <td>
                <grid:link mongoId="{id}" bucket="{bucket}"
                           save="${true}">{filename}</grid:link>
            </td>
            <td>
                {type}
            </td>
            <td>
                {date}
            </td>
            <td>
                <u:deleteLink topic="thesis"
                              id="{id}"
                              confirm="${g.message(code: 'file.delete.confirm.message', args: ['{filename}'])}"
                              onSuccess="removeFromFileList('file-list', 'list-info', data)"
                              params="${[thesisId: thesisInstance.id]}"><i class="icon-trash"></i></u:deleteLink>
            </td>
        </tr>
    </table>

    <u:uploader topic="thesis"
                params="${[id: thesisInstance.id]}"
                callbacks="[complete: 'addToFileList(\'file-list\',\'list-info\')']"/>
</div>
</body>
</html>
