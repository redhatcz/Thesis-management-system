<div class="control-group ${hasErrors(bean: organizationInstance, field: 'name', 'error')} ">
	<label class="control-label" for="organization.name">
		<g:message code="organization.name.label" default="Name" />
	</label>
    <div class="controls">
	    <g:textField name="organization.name" value="${organizationInstance?.name}" placeholder="Name"/>
    </div>
</div>

<div class="control-group">
    <label class="control-label" for="organization.type">
        <g:message code="organization.type.label" default="Type of organization" />
    </label>
    <div class="controls">
        <g:select from="['University', 'Company']" required="" name="organization.type"
                  value="${organizationInstance?.class?.simpleName}"/>
    </div>
</div>

<div class="control-group">
    <label class="control-label" for="users-list">
        <g:message code="organization.users.label" default="Users" />
    </label>
    <div class="controls">
        <richg:dynamicField id="users-list" for="${usersCommand?.users}" var="user" index="i">
            <g:hiddenField name="usersCommand.users[${i}].id" value="${user?.id}"/>
            <a4g:textField name="usersCommand.users[${i}].fullName" value="${user?.fullName}"
                         autocomplete-url="${createLink(controller: 'json', action: 'listUsersByName')}"
                         autocomplete-target="usersCommand.users[${i}].id"/>
        </richg:dynamicField>
    </div>
</div>
