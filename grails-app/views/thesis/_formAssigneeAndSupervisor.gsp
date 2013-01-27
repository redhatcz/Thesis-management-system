<div class="control-group ${hasErrors(bean: thesisInstance?.assignee, field: 'fullName', 'error')}">
    <label class="control-label" for="thesis.assignee.fullName">
        <g:message code="thesis.assignee.label" default="Assignee" />
    </label>
    <div class="controls">
        <g:hiddenField name="thesis.assignee.id" value="${thesisInstance?.assignee?.id}"/>
        <a4g:textField name="thesis.assignee.fullName" value="${thesisInstance?.assignee?.fullName}" required=""
                       autocomplete-url="${createLink(controller: 'json', action: 'listUsersByName')}"
                       autocomplete-target="thesis.assignee.id"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: thesisInstance?.supervisor, field: 'fullName', 'error')} required">
    <label class="control-label" for="thesis.supervisor.id">
        <g:message code="thesis.supervisor.label" default="Supervisor" />
    </label>
    <div class="controls">
        <a4g:select id="thesis.supervisor.id"
                    name="thesis.supervisor.id"
                    source="thesis.assignee.fullName thesis.topic.title"
                    noSelection="${['null':'-- no selection --']}"
                    remote-url="${createLink(controller: 'json', action: 'listSupervisorsForUser')}"
                    remote-opts="thesis.topic.id@topicId thesis.assignee.id@userId"
                    class="many-to-one"/>
    </div>
</div>