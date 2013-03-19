<div id="${id}" class="${classes}">
    ${body}
    <input type="button" class="tms-btn dynamic-delete-button"
           value="Delete" onclick='$("#${id}").remove();'/>
</div>
