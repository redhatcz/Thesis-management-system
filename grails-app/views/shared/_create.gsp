<h2 class="header"><g:message code="default.create.label" args="[entityName]" /></h2>
<g:form class="form-inline" action="save" >
    <g:render template="form"/>
    <div class="control-group">
        <div class="controls">
            <g:submitButton name="create" class="tms-btn"
                            value="${message(code: 'default.button.create.label', default: 'Create')}" />
        </div>
    </div>
</g:form>
