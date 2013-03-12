<div class="control-group ${hasErrors(bean: universityInstance, field: 'name', 'error')} ">
	<label class="control-label" for="university.name">
		<strong><g:message code="university.name.label"/></strong>
	</label>
    <div class="controls">
	    <g:textField name="university.name" value="${universityInstance?.name}" placeholder="${message(code: 'university.name.label')}"/>
    </div>
</div>
