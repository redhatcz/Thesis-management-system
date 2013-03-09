<div id="${wrapperId}"></div>

<g:if test="${!autoUpload}">
    <span id="triggerUpload" class="tms-btn">
        ${message(code: 'uploader.text.startUploadButton', default: 'Start upload')}
    </span>
</g:if>

<script>
    $(document).ready(function () {
        var uploader = $('#${wrapperId}').fineUploader(
            ${config}
        );

        $("#triggerUpload").click(function() {
            uploader.fineUploader('uploadStoredFiles');
        });
    });
</script>
