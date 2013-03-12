<div class="control-group ${hasErrors(bean: thesisInstance, field: 'title', 'error')} ">
    <label class="control-label" for="thesis.title">
        <strong><g:message code="thesis.title.label"/></strong>
    </label>
    <div class="controls">
        <g:textField name="thesis.title" value="${thesisInstance?.title}" placeholder="${message(code: 'thesis.title.label')}" />
    </div>
</div>

<div class="control-group ${hasErrors(bean: thesisInstance?.assignee, field: 'fullName', 'error')}">
    <label class="control-label" for="thesis.assignee.fullName">
        <strong><g:message code="thesis.assignee.label"/></strong>
    </label>
    <div class="controls">
        <g:hiddenField name="thesis.assignee.id" value="${thesisInstance?.assignee?.id}"/>
        <a4g:textField name="thesis.assignee.fullName"
                       value="${thesisInstance?.assignee?.fullName}"
                       disabled="${disabledAssigneeField}"
                       autocomplete-url="${createLink(controller: 'json', action: 'listUsersByName')}"
                       autocomplete-target="thesis.assignee.id"
                       placeholder="${message(code: 'thesis.assignee.label')}"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: thesisInstance, field: 'supervisor', 'error')} required">
    <label class="control-label" for="thesis.supervisor.id">
        <strong><g:message code="thesis.supervisor.label"/></strong>
    </label>
    <div class="controls">
        <g:hiddenField name="thesis.supervisor.id"
                       value="${thesisInstance?.supervisor?.id}"/>
        <g:hiddenField name="a4g-role[${i}]" value="${com.redhat.theses.auth.Role.SUPERVISOR}"/>
        <a4g:textField name="thesis.supervisor.fullName"
                       value="${thesisInstance?.supervisor?.fullName}"
                       autocomplete-url="${createLink(controller: 'json', action: 'listUserByNameAndRole')}"
                       autocomplete-target="thesis.supervisor.id"
                       autocomplete-opts="a4g-role[${i}]@role"
                       placeholder="${message(code: 'thesis.supervisor.label')}"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: thesisInstance, field: 'description', 'error')}">
    <label class="control-label" for="thesis.description">
        <strong><g:message code="thesis.description.label"/></strong>
    </label>
    <div class="controls">
        <g:textArea name="thesis.description" rows="15" value="${thesisInstance?.description}"/>
    </div>
</div>