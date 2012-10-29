<%@ page import="com.redhat.theses.Topic" %>

<div class="fieldcontain ${hasErrors(bean: topicInstance, field: 'title', 'error')} ">
    <label for="title">
        <g:message code="topic.title.label" default="Title" />

    </label>
    <g:textField name="topic.title" value="${topicInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: topicInstance, field: 'owner', 'error')} required">
    <label for="owner">
        <g:message code="topic.owner.label" default="Owner" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="owner" name="topic.owner.id" from="${com.redhat.theses.auth.User.list()}" optionKey="id" required="" value="${topicInstance?.owner?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain">
    <label for="supervison-list">
        <g:message code="supervision.label" default="Supervision" />
    </label>
    <g:render template="membership"/>
</div>

<div class="fieldcontain ${hasErrors(bean: topicInstance, field: 'tags', 'error')} ">
    <label for="tags">
        <g:message code="topic.tags.label" default="Tags" />

    </label>
    <g:select name="topic.tags" from="${com.redhat.theses.Tag.list()}" multiple="multiple" optionKey="id" size="5" value="${topicInstance?.tags*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: topicInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="topic.description.label" default="Description" />
		
	</label>
	<g:textArea name="topic.description" cols="40" rows="5" value="${topicInstance?.description}"/>
</div>


