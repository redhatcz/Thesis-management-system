<script>
    $(document).ready(function () {
       _uploader = $('#${uploaderId}').fineUploader(
                ${config}
        )${callbacks};
    });
</script>

<div ${attrs}>
    <div id="${uploaderId}"></div>
    ${body}
</div>

