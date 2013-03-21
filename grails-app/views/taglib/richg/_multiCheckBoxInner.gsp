<div id="${id}" class="multi-checkbox">
    <label class="checkbox" for="${id}">
        <g:checkBox name="${name}"
                    id="${id}"
                    value="${value}"
                    checked="${checked}"
                    class="${classes}" />
        ${label.encodeAsHTML()}
    </label>
</div>
