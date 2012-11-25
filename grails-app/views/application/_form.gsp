<g:hiddenField id="application.topic.id" name="application.topic.id" value="${application.topic.id}" />
<div class="control-group ${hasErrors(bean: applicationInstance, field: 'university', 'error')} required">
	<label class="control-label" for="application.university.id">
		<g:message code="application.university.label" default="University" />
	</label>
    <div class="controls">
        <g:select id="application.university.id"
                  name="application.university.id"
                  from="${universities}"
                  noSelection="${['null':'-- no selection --']}"
                  optionKey="id"
                  required=""
                  value="${applicationInstance?.university?.id}"
                  class="many-to-one"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: applicationInstance, field: 'supervisor', 'error')} required">
	<label class="control-label" for="application.supervisor.id">
		<g:message code="application.supervisor.label" default="Supervisor" />
	</label>
    <div class="controls">
        <a4g:select id="application.supervisor.id"
                    name="application.supervisor.id"
                    source="application.university.id"
                    noSelection="${['null':'-- no selection --']}"
                    remote-url="${createLink(controller: 'json', action: 'listSupervisorsFromUniversity')}"
                    remote-opts="application.topic.id@topicId application.university.id@organizationId"
                    class="many-to-one"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: applicationInstance, field: 'supervisor', 'error')} required">
    <label class="control-label" for="application.note">
        <g:message code="application.note" default="Note" />
    </label>
    <div class="controls">
        <g:textArea name="application.note"
                    value="${application.note}"
                    cols="" rows=""/>
    </div>
</div>