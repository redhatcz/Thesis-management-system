<h1 class="header">
    <g:message code="default.create.label" args="[entityName]" />
</h1>
<g:form class="form-horizontal" action="save" >
    <g:render template="form"/>
    <div class="control-group">
        <div class="controls">
            <g:submitButton name="create" class="btn btn-primary"
                            value="${message(code: 'default.button.create.label', default: 'Create')}" />
        </div>
    </div>
</g:form>