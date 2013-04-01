<span ${attrs}>
 ${body}
</span>

<script>
    $(document).ready(function () {
        $("#${id}").click(function () {
            //noinspection JSUnresolvedVariable
            _uploader.fineUploader('uploadStoredFiles');
        });
    });
</script>
