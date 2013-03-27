<div id="${id}" class="dynamic-field ${classes}">
    ${body}
    <div id="add-${var}-button">
        <input class="tms-btn"
               type="button"
               value="${message(code: "dynamicField.add.${var}.button", default: "Add")}"
               onclick="addDynamicField('${var}', ${size})"/>
    </div>
    <script type="text/javascript">
        $(".dynamic-field-child.clone").attr("style", "display: none;");
        $(".dynamic-field-child.clone :input").attr("disabled", true);
    </script>
</div>
