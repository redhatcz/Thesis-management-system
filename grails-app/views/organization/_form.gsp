<div class="fieldcontain ${hasErrors(bean: organizationInstance, field: 'name', 'error')} ">
	<label for="organization.name">
		<g:message code="organization.name.label" default="Name" />
		
	</label>
	<g:textField name="organization.name" value="${organizationInstance?.name}"/>
</div>

<div class="fieldcontain">
    <label for="organization.type">
        <g:message code="organization.type.label" default="Type of organization" />

    </label>
    <g:select from="['University', 'Company']" required="" name="organization.type" value="${organizationInstance?.class?.simpleName}"/>
</div>

<div class="fieldcontain">
    <label for="users-list">
        <g:message code="organization.users.label" default="Users" />

    </label>
    <richg:dynamicField id="users-list" for="${usersCommand?.users}" var="user" index="i">
        <g:hiddenField name="usersCommand.users[${i}].id" value="${user?.id}"/>
        <a4g:textField name="usersCommand.users[${i}].fullName" value="${user?.fullName}"
                     autocomplete-url="${createLink(controller: 'json', action: 'listUsersByName')}"
                     autocomplete-target="usersCommand.users[${i}].id"/>
    </richg:dynamicField>
</div>
