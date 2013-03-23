<div id="${wrapperId}"></div>

<g:if test="${!autoUpload}">
<div class="qq-trigger-btn">
    <span id="triggerUpload" class="tms-btn">
        <i class="icon-upload"></i>
        ${message(code: 'uploader.text.startUploadButton', default: 'Start Upload')}
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
