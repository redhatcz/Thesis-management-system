<script>
    $(document).ready(function () {
       _uploader = $('#${uploaderId}').fineUploader(
                ${config}
        )${callbacks};
    });
</script>

<div class="uploader-manage" ${attrs}>
    ${body}
</div>

