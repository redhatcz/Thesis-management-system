<div class="control-group ${hasErrors(bean: topicInstance, field: 'title', 'error')} ">
    <label class="control-label" for="topic.title">
        <g:message code="topic.title.label" default="Title" />
    </label>
    <div class="controls">
        <g:textField name="topic.title" value="${topicInstance?.title}" placeholder="Title" />
    </div>
</div>

<div class="control-group ${hasErrors(bean: topicInstance, field: 'owner', 'error')} required">
    <label class="control-label" for="topic.owner.id">
        <g:message code="topic.owner.label" default="Owner" />
        <span class="required-indicator">*</span>
    </label>
    <div class="controls">
        <g:hiddenField name="topic.owner.id" value="${topicInstance?.owner?.id}"/>
        <a4g:textField name="topic.owner.fullName" value="${topicInstance?.owner?.fullName}"
                       autocomplete-url="${createLink(controller: 'json', action: 'listUsersByName')}"
                       autocomplete-target="topic.owner.id"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: topicInstance, field: 'company', 'error')} required">
    <label class="control-label" for="company">
        <g:message code="topic.company.label" default="Company" />
        <span class="required-indicator">*</span>
    </label>
    <div class="controls">
        <g:select id="company" name="topic.company.id" from="${com.redhat.theses.Company.list()}"
                  optionKey="id" required="" value="${topicInstance?.company?.id}" class="many-to-one"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: topicInstance, field: 'universities', 'error')}">
    <label class="control-label" for="topic.universities">
        <g:message code="topic.universities.label" default="Universities" />
    </label>
    <div class="controls">
        <g:select name="topic.universities" from="${universities}" multiple="multiple"
                  optionKey="id" size="5" value="${topicInstance?.universities*.id}" class="many-to-many"/>
    </div>
</div>

<div class="control-group">
    <label class="control-label" for="supervison-list">
        <g:message code="supervision.label" default="Supervision" />
    </label>
    <div class="controls">
        <g:render template="membership"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: topicInstance, field: 'tags', 'error')}">
    <label class="control-label" for="topic.tags">
        <g:message code="topic.tags.label" default="Tags" />
    </label>
    <div class="controls">
        <g:select name="topic.tags" from="${com.redhat.theses.Tag.list()}" multiple="multiple"
                  optionKey="id" size="5" value="${topicInstance?.tags*.id}" class="many-to-many"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: topicInstance, field: 'lead', 'error')}">
    <label class="control-label" for="topic.lead">
        <g:message code="topic.lead.label" default="Lead Paragraph" />
    </label>
    <div class="controls">
        <g:textArea name="topic.lead" cols="80" rows="5" value="${topicInstance?.lead}"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: topicInstance, field: 'description', 'error')}">
	<label class="control-label" for="topic.description">
		<g:message code="topic.description.label" default="Description" />		
	</label>
    <div class="controls">
	   <g:textArea name="topic.description" cols="80" rows="15" value="${topicInstance?.description}"/>
    </div>
</div>


