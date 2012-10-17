<g:textField name="${name}" value="${value}"/>
<script type="text/javascript">
    $(document).ready(function() {
        autocompletion("${name}", "${hiddenFieldId}", "${remoteUrl}", "${optElements}");
    });
</script>