<div class="control-group ${hasErrors(bean: thesisInstance, field: 'thesisAbstract', 'error')}">
    <label class="control-label" for="thesis.thesisAbstract">
        <strong><g:message code="thesis.thesisAbstract.label" /></strong>
    </label>
    <div class="controls">
        <g:textArea name="thesis.thesisAbstract"
                    cols="80" rows="5"
                    value="${thesisInstance?.thesisAbstract}"
                    placeholder="${message(code: 'thesis.thesisAbstract.missing')}" />
    </div>
</div>
