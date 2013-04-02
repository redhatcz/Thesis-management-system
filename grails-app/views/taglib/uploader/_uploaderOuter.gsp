<script>
    uploader_displayMessages = function(data) {
        if (!data.success && data.message) {
            $('#' + "${errorId}").html(data.message);
        }
    }

    uploader_onComplete = function(event, id, name, data) {
        if ("${errorId}") {
            uploader_displayMessages(data);
        }
        ${onComplete}
    }

    $(document).ready(function () {
       _uploader = $('#${uploaderId}').fineUploader(
                ${config}
       ).on('complete', uploader_onComplete)${callbacks};
    });
</script>

<div class="uploader-manage" ${attrs}>
    ${body}
</div>

