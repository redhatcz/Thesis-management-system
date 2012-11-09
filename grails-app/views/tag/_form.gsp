<%@ page import="com.redhat.theses.Tag" %>



<div class="fieldcontain ${hasErrors(bean: tagInstance, field: 'title', 'error')} ">
	<label for="tag.title">
		<g:message code="tag.title.label" default="Title" />
		
	</label>
	<g:textField name="tag.title" value="${tagInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: tagInstance, field: 'parent', 'error')} ">
	<label for="tag.parent.id">
		<g:message code="tag.parent.label" default="Parent" />
	</label>
    <g:hiddenField name="tag.parent.id" value="${tagInstance?.parent?.id}"/>
    <a4g:textField name="tag.parent.title" value="${tagInstance?.parent?.title}"
                   autocomplete-url="${createLink(controller: 'json', action: 'listTagsByName')}"
                   autocomplete-target="tag.parent.id"/>
</div>

<div class="fieldcontain ${hasErrors(bean: tagInstance, field: 'subTags', 'error')} ">
	<label for="tag.subTags">
		<g:message code="tag.subTags.label" default="Sub Tags" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${tagInstance?.subTags?}" var="s">
    <li><g:link controller="tag" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="tag" action="create" params="['tag.id': tagInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'tag.label', default: 'Tag')])}</g:link>
</li>
</ul>

</div>

