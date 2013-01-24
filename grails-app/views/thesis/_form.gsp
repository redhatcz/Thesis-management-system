<div class="control-group ${hasErrors(bean: thesisInstance?.assignee, field: 'fullName', 'error')}">
    <label class="control-label" for="thesis.assignee.fullName">
        <g:message code="thesis.assignee.label" default="Assignee" />
    </label>
    <div class="controls">
        <g:hiddenField name="thesis.assignee.id" value="${thesisInstance?.assignee?.id}"/>
        <a4g:textField name="thesis.assignee.fullName" value="${thesisInstance?.assignee?.fullName}" required=""
                       autocomplete-url="${createLink(controller: 'json', action: 'listUsersByName')}"
                       autocomplete-target="thesis.assignee.id"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: thesisInstance, field: 'status', 'error')}">
    <label class="control-label" for="thesis.status">
        <g:message code="thesis.status.label" default="Status" />
    </label>
    <div class="controls">
        <g:select name="thesis.status"
                  from="${statusList}"
                  required=""
                  value="${thesisInstance?.status?.toString()}"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: thesisInstance, field: 'grade', 'error')}">
    <label class="control-label" for="thesis.grade">
        <g:message code="thesis.grade.label" default="Grade" />
    </label>
    <div class="controls">
        <g:select name="thesis.grade"
                  from="${gradeList}"
                  noSelection="['null':'-- no selection --']"
                  value="${thesisInstance?.grade?.toString()}"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: thesisInstance, field: 'thesisAbstract', 'error')}">
    <label class="control-label" for="thesis.thesisAbstract">
        <g:message code="thesis.abstract.label" default="Abstract" />
    </label>
    <div class="controls">
        <g:textArea name="thesis.thesisAbstract" cols="80" rows="5" value="${thesisInstance?.thesisAbstract}"/>
    </div>
</div>