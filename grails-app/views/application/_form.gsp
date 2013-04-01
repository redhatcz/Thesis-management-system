<g:hiddenField id="application.topic.id" name="application.topic.id" value="${application.topic.id}" />
<div class="control-group ${hasErrors(bean: applicationInstance, field: 'university', 'error')}">
  	<label class="control-label" for="application.university.id">
  		  <strong><g:message code="university.label"/></strong>
  	</label>
        <div class="controls">
            <g:select id="application.university.id"
                    name="application.university.id"
                    from="${universities}"
                    noSelection="['':message(code: 'no.selection.label')]"
                    optionKey="id"
                    value="${applicationInstance?.university?.id}"
                    class="many-to-one"/>
        </div>
</div>

<div class="control-group ${hasErrors(bean: applicationInstance, field: 'note', 'error')}">
    <label class="control-label" for="application.note">
        <strong><g:message code="application.note.label"/></strong>
    </label>
    <div class="controls">
        <g:textArea name="application.note" rows="5" value="${application.note}" />
    </div>
</div>
