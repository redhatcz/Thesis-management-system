<div class="control-group ${hasErrors(bean: thesisInstance?.assignee, field: 'fullName', 'error')}">
    <label class="control-label" for="thesis.assignee.fullName">
        <strong><g:message code="thesis.assignee.label" default="Assignee" /></strong>
    </label>
    <div class="controls">
        <g:hiddenField name="thesis.assignee.id" value="${thesisInstance?.assignee?.id}"/>
        <a4g:textField name="thesis.assignee.fullName" value="${thesisInstance?.assignee?.fullName}"
                       autocomplete-url="${createLink(controller: 'json', action: 'listUsersByName')}"
                       autocomplete-target="thesis.assignee.id"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: thesisInstance, field: 'supervisor', 'error')} required">
    <label class="control-label" for="thesis.supervisor.id">
        <strong><g:message code="thesis.supervisor.label" default="Supervisor" /></strong>
    </label>
    <div class="controls">
        <g:hiddenField name="thesis.supervisor.id" value="${thesisInstance?.supervisor?.id}"/>
        <a4g:textField name="thesis.supervisor.fullName" value="${thesisInstance?.supervisor?.fullName}" required=""
                       autocomplete-url="${createLink(controller: 'json', action: 'listSupervisorsByName')}"
                       autocomplete-target="thesis.supervisor.id"/>
    </div>
</div>
