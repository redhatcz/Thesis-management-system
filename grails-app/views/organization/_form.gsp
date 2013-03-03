<div class="control-group ${hasErrors(bean: organizationInstance, field: 'name', 'error')} ">
	<label class="control-label" for="organization.name">
		<strong><g:message code="organization.name.label" default="Name" /></strong>
	</label>
    <div class="controls">
	    <g:textField name="organization.name" value="${organizationInstance?.name}" placeholder="Name"/>
    </div>
</div>

<div class="control-group">
    <label class="control-label" for="organization.type">
        <strong><g:message code="organization.type.label" default="Type of organization" /></strong>
    </label>
    <div class="controls">
        <g:select from="['University', 'Company']" required="" name="organization.type"
                  value="${organizationInstance?.class?.simpleName}"/>
    </div>
</div>
