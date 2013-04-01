<div class="avatar">
    <richg:avatar user="${userInstance}"/>
    <!-- Button to trigger modal -->
    <a href="#change-avatar" role="button" id="avatar-button" data-toggle="modal">
        <g:message code="uploader.text.changeAvatar.button"/>
    </a>
</div>
<!-- Modal -->
<div id="change-avatar" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3><g:message code="uploader.text.changeAvatar.title"/></h3>
    </div>
    <div class="modal-body">
        <u:uploader topic="avatar" params="${[id: userInstance.id]}">
            <u:deleteLink class="tms-btn pull-right"
                          id="${userInstance.id}" topic="avatar" >
                <g:message code="avatar.delete.button"/>
            </u:deleteLink>
            <u:trigger id="triggerUpload" class="tms-btn pull-right">
                <i class="icon-upload"></i>
                <g:message code="uploader.text.startUpload.button"/>
            </u:trigger>
        </u:uploader>
    </div>
</div>