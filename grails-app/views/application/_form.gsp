<%@ page import="com.redhat.theses.Type" %>

<g:hiddenField id="application.topic.id"
               name="application.topic.id"
               value="${applicationInstance.topic.id}" />
<div class="control-group ${hasErrors(bean: applicationInstance, field: 'university', 'error')} required">
    <div class="control-with-msg">
        <div class="small-msg pull-right">
            <i class="icon-info-sign"
               title="${message(code:'info.application.create.university')}"></i>
        </div>
        <label class="control-label" for="application.university.id">
            <strong><g:message code="university.label"/></strong>
            <strong><span class="required-indicator">*</span></strong>
        </label>
    </div>
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

<div class="control-group ${hasErrors(bean: applicationInstance, field: 'type', 'error')} required">
    <div class="control-with-msg">
        <div class="small-msg pull-right">
            <i class="icon-info-sign"
               title="${message(code:'info.application.create.type')}"></i>
        </div>
        <label class="control-label" for="application.type">
            <strong><g:message code="application.type.label"/></strong>
            <strong><span class="required-indicator">*</span></strong>
        </label>
    </div>
    <div class="controls">
        <g:select name="application.type"
                  from="${Type.values()}"
                  noSelection="['':message(code: 'no.selection.label')]"
                  optionValue="${{g.message(code:"application.type.${it?.toString()?.toLowerCase()}.label")}}"
                  value="${applicationInstance?.type?.toString()}"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: applicationInstance, field: 'note', 'error')}">
    <div class="small-msg pull-right">
        <i class="icon-info-sign"
           title="${message(code:'info.application.create.note')}"></i>
    </div>
    <label class="control-label" for="application.note">
        <strong><g:message code="application.note.label"/></strong>
    </label>
    <div class="controls">
        <g:textArea name="application.note" rows="5" value="${application.note}" />
    </div>
</div>
