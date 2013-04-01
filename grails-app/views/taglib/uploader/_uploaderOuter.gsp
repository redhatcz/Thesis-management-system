<script>
    $(document).ready(function () {
       _uploader = $('#${wrapperId}').fineUploader(
                ${config}
        )${callbacks};
    });
</script>

<div>
    <div id="${wrapperId}"></div>
    ${body}
</div>

