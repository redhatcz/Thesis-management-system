<div class="fieldcontain ${hasErrors(bean: topicInstance, field: 'title', 'error')} ">
    <label for="topic.title">
        <g:message code="topic.title.label" default="Title" />

    </label>
    <g:textField name="topic.title" value="${topicInstance?.title}"/>
</div>


<div class="fieldcontain ${hasErrors(bean: topicInstance, field: 'owner', 'error')} required">
    <label for="topic.owner.id">
        <g:message code="topic.owner.label" default="Owner" />
        <span class="required-indicator">*</span>
    </label>
    <a4g:autocomplete remoteUrl="${createLink(controller: 'json', action: 'listUsersByName')}">
        <g:hiddenField name="topic.owner.id" value="${topicInstance?.owner?.id}"/>
        <g:textField name="topic.owner.fullName" value="${topicInstance?.owner?.fullName}"/>
    </a4g:autocomplete>
</div>

<div class="fieldcontain ${hasErrors(bean: topicInstance, field: 'company', 'error')} required">
    <label for="company">
        <g:message code="topic.company.label" default="Company" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="company" name="topic.company.id" from="${com.redhat.theses.Company.list()}" optionKey="id" required="" value="${topicInstance?.company?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain">
    <label for="supervison-list">
        <g:message code="supervision.label" default="Supervision" />
    </label>
    <g:render template="membership"/>
</div>

<div class="fieldcontain ${hasErrors(bean: topicInstance, field: 'tags', 'error')} ">
    <label for="topic.tags">
        <g:message code="topic.tags.label" default="Tags" />

    </label>
    <g:select name="topic.tags" from="${com.redhat.theses.Tag.list()}" multiple="multiple" optionKey="id" size="5" value="${topicInstance?.tags*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: topicInstance, field: 'description', 'error')} ">
	<label for="topic.description">
		<g:message code="topic.description.label" default="Description" />
		
	</label>
	<g:textArea name="topic.description" cols="40" rows="5" value="${topicInstance?.description}"/>
</div>


