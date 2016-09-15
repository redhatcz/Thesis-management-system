<div class="control-group ${hasErrors(bean: universityInstance, field: 'name', 'error')} required">
    <div class="control-with-msg">
        <label class="control-label" for="university.name">
            <strong><g:message code="university.name.label"/></strong>
            <span class="required-indicator">*</span></strong>
        </label>
    </div>
    <div class="controls half">
        <g:textField name="university.name" value="${universityInstance?.name}" placeholder="${message(code: 'university.name.label')}"/>
    </div>
    <div class="info">
        <p><g:message code="${message(code:'info.university.create.name')}"/></p>
    </div>
</div>
<div class="control-group ${hasErrors(bean: universityInstance, field: 'acronym', 'error')} required">
    <div class="control-with-msg">
        <label class="control-label" for="university.acronym">
            <strong><g:message code="university.acronym.label"/></strong>
            <span class="required-indicator">*</span></strong>
        </label>
    </div>
    <div class="controls half">
        <g:textField name="university.acronym" value="${universityInstance?.acronym}" placeholder="${message(code: 'university.acronym.label')}"/>
    </div>
    <div class="info">
        <p><g:message code="${message(code:'info.university.create.acronym')}"/></p>
    </div>
</div>
