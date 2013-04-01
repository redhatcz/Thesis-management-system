<div class="file-uploader">
    <h3 id="file-upload">
        <i class="icon-folder-open"></i>
        <g:message code="uploader.uploadedFiles.header"/>
    </h3>
    <g:set var="show" value="${files || false}"/>

    <p class="well" id="list-info" style="${show ? 'display: none' : ''}"><g:message code="file.no.files.uploaded"/></p>

    <table class="table" style="${!show ? 'display: none' : ''}" id="file-list">
        <thead>
        <tr>
            <th><g:message code="file.label"/></th>
            <th><g:message code="file.type.label"/></th>
            <th><g:message code="file.uploadDate.label"/></th>
            <g:if test="${isAuthorized}">
                <th><g:message code="file.action.label"/></th>
            </g:if>
        </tr>
        </thead>
        <g:each in="${files}" var="f">
            <tr data-file="${f.id}" r>
                <td>
                    <grid:link mongoId="${f.id}" bucket="thesis"
                               save="${true}">${f.filename.encodeAsHTML()}</grid:link>
                </td>
                <td>
                    ${f.contentType.encodeAsHTML()}
                </td>
                <td>
                    <g:formatDate date="${f.uploadDate}" dateStyle="LONG" type="date"/>
                </td>
                <g:if test="${isAuthorized}">
                <td>
                    <u:deleteLink topic="thesis"
                                  id="${f.id}"
                                  confirm="${g.message(code: 'file.delete.confirm.message', args: [f.filename])}"
                                  onSuccess="removeFromFileList('file-list', 'list-info', data)"
                                  params="${[thesisId: thesisInstance.id]}"><i class="icon-trash"></i></u:deleteLink>
                </td>
                </g:if>
            </tr>
        </g:each>
        <g:if test="${isAuthorized}">
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
                    <u:deleteLink topic="thesis" id="{id}"
                                  confirm="${g.message(code: 'file.delete.confirm.message', args: ['{filename}'])}"
                                  onSuccess="removeFromFileList('file-list', 'list-info', data)"
                                  params="${[thesisId: thesisInstance.id]}"><i class="icon-trash"></i></u:deleteLink>
                </td>
            </tr>
        </g:if>
    </table>

    <g:if test="${isAuthorized}">
        <u:uploader topic="thesis" params="${[id: thesisInstance.id]}"
                    callbacks="[complete: 'addToFileList(\'file-list\',\'list-info\')']">
            <u:trigger id="triggerUpload" class="tms-btn pull-right">
                <i class="icon-upload"></i>
                <g:message code="uploader.text.startUpload.button"/>
            </u:trigger>
        </u:uploader>
    </g:if>
</div>