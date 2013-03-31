<div class="control-group ${hasErrors(bean: thesisInstance, field: 'thesisAbstract', 'error')}">
    <label class="control-label" for="thesis.thesisAbstract">
        <strong><g:message code="thesis.thesisAbstract.label" /></strong>
    </label>
    <div class="controls">
        <g:textArea name="thesis.thesisAbstract"
                    cols="80" rows="5"
                    value="${thesisInstance?.thesisAbstract}"/>
    </div>
</div>

<g:render template="/shared/thesis/formCommon"/>

<sec:ifAnyGranted roles="ROLE_OWNER, ROLE_ADMIN, ROLE_SUPERVISOR">
    <div class="control-group ${hasErrors(bean: thesisInstance, field: 'status', 'error')}">
        <label class="control-label" for="thesis.status">
            <strong><g:message code="thesis.status.label"/></strong>
        </label>
        <div class="controls">
            <g:select name="thesis.status"
                      from="${statusList}"
                      value="${thesisInstance?.status?.toString()}"/>
        </div>
    </div>

    <div class="control-group ${hasErrors(bean: thesisInstance, field: 'grade', 'error')}">
        <label class="control-label" for="thesis.grade">
            <strong><g:message code="thesis.grade.label"/></strong>
        </label>
        <div class="controls">
            <g:select name="thesis.grade"
                      from="${gradeList}"
                      noSelection="['':message(code: 'no.selection.label')]"
                      value="${thesisInstance?.grade?.toString()}"/>
        </div>
    </div>
</sec:ifAnyGranted>
