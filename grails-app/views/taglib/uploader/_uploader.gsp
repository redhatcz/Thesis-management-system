<div id="${wrapperId}"></div>

<g:if test="${!autoUpload}">
<div class="qq-trigger-btn">
    <span id="triggerUpload" class="tms-btn pull-right">
        <i class="icon-upload"></i>
        <g:message code="uploader.text.startUploadButton"/>
    </span>
</div>
</g:if>

<script>
    $(document).ready(function () {
        var uploader = $('#${wrapperId}').fineUploader(
            ${config}
        )${callbacks};

        $("#triggerUpload").click(function() {
            uploader.fineUploader('uploadStoredFiles');
        });
    });
</script>
