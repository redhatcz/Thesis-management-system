<div id="${wrapperId}"></div>

<g:if test="${!autoUpload}">
    <div id="triggerUpload" class="btn btn-primary">
        <i class="icon-upload icon-white"></i>
        ${message(code: 'uploader.text.startUploadButton', default: 'Start upload')}
    </div>
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