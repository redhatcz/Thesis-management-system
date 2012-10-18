<div id="dynamic-field-${var}-${i}" class="dynamic-field-child" style="${i == 'clone' ? 'display: none;' : ''}">
    ${body}
    <input type="button" class="delete" value="Delete" onclick="$('#dynamic-field-${var}-${i}').remove();"/>
</div>