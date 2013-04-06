<div class="multi-checkbox-child">
    <input type="hidden" name="_${name}"/>
    <label class="checkbox" for="${id}">
        <input type="checkbox"
               name="${name}"
               id="${id}"
               class="${classes}"
               value="${value}"
               ${checked ? checked="checked" : ''}/>
        ${label.encodeAsHTML()}
    </label>
</div>
