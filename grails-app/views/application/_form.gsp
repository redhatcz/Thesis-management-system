<g:hiddenField id="application.topic.id" name="application.topic.id" value="${application.topic.id}" />
<div class="control-group ${hasErrors(bean: applicationInstance, field: 'university', 'error')} required">
  	<label class="control-label" for="application.university.id">
  		<strong><g:message code="application.university.label" default="University" /></strong>
  	</label>
        <div class="controls">
            <g:select id="application.university.id"
                    name="application.university.id"
                    from="${universities}"
                    noSelection="${['null':'-- no selection --']}"
                    optionKey="id" value="${applicationInstance?.university?.id}"
                    class="many-to-one"/>
        </div>
</div>

<div class="control-group ${hasErrors(bean: applicationInstance, field: 'supervisor', 'error')} required">
    <label class="control-label" for="application.supervisor.id">
    	<strong><g:message code="application.supervisor.label" default="Preferred supervisor" /></strong>
    </label>
        <div class="controls">
          <a4g:select id="application.supervisor.id"
                      name="application.supervisor.id"
                      source="application.university.id"
                      noSelection="${['null':'-- no selection --']}"
                      remote-url="${createLink(controller: 'json', action: 'listSupervisorsFromUniversity')}"
                      remote-opts="application.topic.id@topicId application.university.id@universityId"
                      class="many-to-one"/>
        </div>
</div>

<div class="control-group ${hasErrors(bean: applicationInstance, field: 'supervisor', 'error')} required">
    <label class="control-label" for="application.note">
        <strong><g:message code="application.note" default="Note" /></strong>
    </label>
    <div class="controls">
        <g:textArea name="application.note"
                    value="${application.note}"
                    cols="" rows=""/>
    </div>
</div>
