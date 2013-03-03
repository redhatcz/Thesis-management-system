<%@ page import="com.redhat.theses.Tag" %>
<div class="control-group ${hasErrors(bean: tagInstance, field: 'title', 'error')} ">
	<label class="control-label" for="tag.title">
		<strong><g:message code="tag.title.label" default="Title" /></strong>
	</label>
    <div class="controls">
        <g:textField name="tag.title" value="${tagInstance?.title}" placeholder="Title" />
    </div>
</div>

<div class="control-group ${hasErrors(bean: tagInstance, field: 'parent', 'error')} ">
	<label class="control-label" for="tag.parent.id">
		<strong><g:message code="tag.parent.label" default="Parent" /></strong>
	</label>
    <div class="controls">
        <g:hiddenField name="tag.parent.id" value="${tagInstance?.parent?.id}"/>
        <a4g:textField name="tag.parent.title" value="${tagInstance?.parent?.title}"
                       autocomplete-url="${createLink(controller: 'json', action: 'listTagsByName')}"
                       autocomplete-target="tag.parent.id" placeholder="Parent" />
    </div>
</div>

<div class="control-group ${hasErrors(bean: tagInstance, field: 'subTags', 'error')} ">
	<label class="control-label" for="tag.subTags">
		<strong><g:message code="tag.subTags.label" default="Sub Tags" /></strong>
	</label>
    <div class="controls">
        <label class="text">
            <g:if test="${tagInstance?.subTags == null}">
                No subtags.
            </g:if>
            <g:else>
                <g:each in="${tagInstance?.subTags?}" var="s">
                    <g:link controller="tag" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link>
                </g:each>
            </g:else>
        </label>
    </div>
</div>

