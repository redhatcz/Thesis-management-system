<%@ page import="com.redhat.theses.Category" %>
<div class="control-group ${hasErrors(bean: categoryInstance, field: 'title', 'error')} ">
	<label class="control-label" for="category.title">
		<strong><g:message code="category.title.label" default="Title" /></strong>
	</label>
    <div class="controls">
        <g:textField name="category.title" value="${categoryInstance?.title}" placeholder="Title" />
    </div>
</div>

<div class="control-group ${hasErrors(bean: categoryInstance, field: 'description', 'error')}">
    <label class="control-label" for="category.description">
        <strong><g:message code="category.description.label" default="Description" /></strong>
    </label>
    <div class="controls">
        <g:textArea name="category.description" rows="15" value="${categoryInstance?.description}"/>
    </div>
</div>

