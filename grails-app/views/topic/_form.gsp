<%@ page import="com.redhat.theses.Topic" %>



<div class="fieldcontain ${hasErrors(bean: topicInstance, field: 'primaryAnnotation', 'error')} ">
	<label for="primaryAnnotation">
		<g:message code="topic.primaryAnnotation.label" default="Primary Annotation" />
		
	</label>
	<g:textArea name="primaryAnnotation" cols="40" rows="5" value="${topicInstance?.primaryAnnotation}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: topicInstance, field: 'secondaryAnnotation', 'error')} ">
	<label for="secondaryAnnotation">
		<g:message code="topic.secondaryAnnotation.label" default="Secondary Annotation" />
		
	</label>
	<g:textArea name="secondaryAnnotation" cols="40" rows="5" value="${topicInstance?.secondaryAnnotation}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: topicInstance, field: 'owner', 'error')} required">
	<label for="owner">
		<g:message code="topic.owner.label" default="Owner" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="owner" name="owner.id" from="${com.redhat.theses.auth.User.list()}" optionKey="id" required="" value="${topicInstance?.owner?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: topicInstance, field: 'supervison', 'error')} ">
	<label for="supervison">
		<g:message code="topic.supervison.label" default="Supervison" />
		
	</label>
	<g:select name="supervison" from="${com.redhat.theses.Membership.list()}" multiple="multiple" optionKey="id" size="5" value="${topicInstance?.supervison*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: topicInstance, field: 'tags', 'error')} ">
	<label for="tags">
		<g:message code="topic.tags.label" default="Tags" />
		
	</label>
	<g:select name="tags" from="${com.redhat.theses.Tag.list()}" multiple="multiple" optionKey="id" size="5" value="${topicInstance?.tags*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: topicInstance, field: 'title', 'error')} ">
	<label for="title">
		<g:message code="topic.title.label" default="Title" />
		
	</label>
	<g:textField name="title" value="${topicInstance?.title}"/>
</div>

