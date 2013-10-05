<%@ page import="com.redhat.theses.Category" %>
<div class="control-group ${hasErrors(bean: categoryInstance, field: 'title', 'error')} ">
    <div class="control-with-msg">
        <div class="small-msg pull-right">
            <i class="icon-info-sign"
               title="${message(code:'info.category.create.title')}"></i>
        </div>
        <label class="control-label" for="category.title">
            <strong><g:message code="category.title.label" /></strong>
        </label>
    </div>
    <div class="controls">
        <g:textField name="category.title" value="${categoryInstance?.title}"
                     placeholder="${message(code: 'category.title.label')}" />
    </div>
</div>

<div class="control-group ${hasErrors(bean: categoryInstance, field: 'description', 'error')}">
    <div class="small-msg pull-right">
        <i class="icon-info-sign"
           title="${message(code:'info.category.create.description')}"></i>
    </div>
    <label class="control-label" for="category.description">
        <strong><g:message code="category.description.label"/></strong>
    </label>
    <div class="controls">
        <g:textArea name="category.description" rows="7" value="${categoryInstance?.description}"/>
    </div>
</div>

