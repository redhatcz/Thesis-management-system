<div class="control-group ${hasErrors(bean: thesisInstance?.topic, field: 'title', 'error')}">
    <label class="control-label" for="thesis.topic.title">
        <g:message code="thesis.topic.title.label" default="Topic" />
    </label>
    <div class="controls">
        <g:hiddenField name="thesis.topic.id" value="${thesisInstance?.topic?.id}"/>
        <a4g:textField name="thesis.topic.title" value="${thesisInstance?.topic?.title}"
                       required="" disabled="${disabledTopicField}"
                       autocomplete-url="${createLink(controller: 'json', action: 'listTopicsByTitle')}"
                       autocomplete-target="thesis.topic.id"/>
    </div>
</div>

<g:render template="formCommon"/>