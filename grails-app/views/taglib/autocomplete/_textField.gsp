<g:textField name="${name}" value="${value}"/>
<script type="text/javascript">
    $(document).ready(function() {
        autocompletion("${jQuery.escapeSelector(value: name)}",
                "${jQuery.escapeSelector(value: hiddenFieldId)}",
                "${remoteUrl}",
                "${optElements}");
    });
</script>