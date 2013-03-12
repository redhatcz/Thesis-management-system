<g:hiddenField id="application.topic.id" name="application.topic.id" value="${application.topic.id}" />
<div class="control-group ${hasErrors(bean: applicationInstance, field: 'university', 'error')} required">
  	<label class="control-label" for="application.university.id">
  		  <strong><g:message code="university.label"/></strong>
  	</label>
        <div class="controls">
            <g:select id="application.university.id"
                    name="application.university.id"
                    from="${universities}"
                    noSelection="${['null':'-- no selection --']}"
                    optionKey="id"
                    value="${applicationInstance?.university?.id}"
                    class="many-to-one"/>
        </div>
</div>

<div class="control-group ${hasErrors(bean: applicationInstance, field: 'note', 'error')} required">
    <label class="control-label" for="application.note">
        <strong><g:message code="application.note.label"/></strong>
    </label>
    <div class="controls">
        <g:textArea name="application.note" value="${application.note}" />
    </div>
</div>
