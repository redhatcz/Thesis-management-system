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

<div class="control-group ${hasErrors(bean: applicationInstance, field: 'university', 'error')} required">
    <label class="control-label" for="application.university.id">
        <g:message code="application.university.label" default="University" />
    </label>
    <div class="controls">
        <a4g:select id="thesis.sMembership.organization.id"
                  name="thesis.sMembership.organization.id"
                  value="${thesisInstance?.sMembership?.organization?.id}"
                  from="${universities}"
                  optionKey="id"
                  source="thesis.assignee.fullName thesis.topic.title"
                  noSelection="${['null':'-- no selection --']}"
                  remote-url="${createLink(controller: 'json', action: 'listUniversitiesForUserWithinTopic')}"
                  remote-opts="thesis.assignee.id@userId thesis.topic.id@topicId"
                  class="many-to-one"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: thesisInstance?.sMembership, field: 'id', 'error')} required">
    <label class="control-label" for="thesis.supervision.id">
        <g:message code="thesis.supervisor.label" default="Supervisor" />
    </label>
    <div class="controls">
        <a4g:select id="thesis.sMembership.id"
                    name="thesis.sMembership.id"
                    value="${thesisInstance?.sMembership?.id}"
                    from="${memberships}"
                    optionKey="id"
                    optionValue="user"
                    source="thesis.sMembership.organization.id"
                    noSelection="${['null':'-- no selection --']}"
                    remote-url="${createLink(controller: 'json', action: 'listMembershipsWithinOrganization')}"
                    remote-opts="thesis.topic.id@topicId thesis.sMembership.organization.id@organizationId"
                    class="many-to-one"/>
    </div>
</div>