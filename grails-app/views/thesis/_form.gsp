<sec:ifAnyGranted roles="ROLE_OWNER, ROLE_ADMIN, ROLE_SUPERVISOR">
    <div class="control-group ${hasErrors(bean: thesisInstance, field: 'title', 'error')} required">
        <label class="control-label" for="thesis.title">
            <strong><g:message code="thesis.title.label"/></strong>
            <span class="required-indicator">*</span></strong>
        </label>
        <div class="controls">
            <g:textField name="thesis.title" value="${thesisInstance?.title}" placeholder="${message(code: 'thesis.title.label')}" />
        </div>
    </div>

    <div class="control-group ${hasErrors(bean: thesisInstance?.assignee, field: 'fullName', 'error')} required">
        <label class="control-label" for="thesis.assignee.fullName">
            <strong><g:message code="thesis.assignee.label"/></strong>
            <span class="required-indicator">*</span></strong>
        </label>
        <div class="controls">
            <g:hiddenField name="thesis.assignee.id" value="${thesisInstance?.assignee?.id}"/>
            <a4g:textField name="thesis.assignee.fullName"
                           value="${thesisInstance?.assignee?.fullName}"
                           disabled="${disabledAssigneeField}"
                           data-autocomplete-url="${createLink(controller: 'json', action: 'listUsersByName')}"
                           data-autocomplete-target="thesis.assignee.id"
                           placeholder="${message(code: 'thesis.assignee.label')}"/>
        </div>
    </div>

    <div class="control-group ${hasErrors(bean: thesisInstance, field: 'supervisor', 'error')} required">
        <label class="control-label" for="thesis.supervisor.fullName">
            <strong><g:message code="thesis.supervisor.label"/></strong>
            <span class="required-indicator">*</span></strong>
        </label>
        <div class="controls">
            <g:hiddenField name="thesis.supervisor.id"
                           value="${thesisInstance?.supervisor?.id}"/>
            <g:hiddenField name="a4g-role[${i}]" value="${com.redhat.theses.auth.Role.SUPERVISOR}"/>
            <a4g:textField name="thesis.supervisor.fullName"
                           value="${thesisInstance?.supervisor?.fullName}"
                           data-autocomplete-url="${createLink(controller: 'json', action: 'listUsersByNameAndRole')}"
                           data-autocomplete-target="thesis.supervisor.id"
                           data-autocomplete-opts="a4g-role[${i}]@role"
                           placeholder="${message(code: 'thesis.supervisor.label')}"/>
        </div>
    </div>

    <!-- If creating new thesis disable status and grade fields -->
    <g:if test="${thesisInstance?.id}">
        <div class="control-group ${hasErrors(bean: thesisInstance, field: 'status', 'error')}">
            <label class="control-label" for="thesis.status">
                <strong><g:message code="thesis.status.label"/></strong>
            </label>
            <div class="controls">
                <g:select name="thesis.status"
                          from="${statusList}"
                          noSelection="['':message(code: 'no.selection.label')]"
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
    </g:if>
</sec:ifAnyGranted>

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

<sec:ifAnyGranted roles="ROLE_OWNER, ROLE_ADMIN, ROLE_SUPERVISOR">
    <div class="control-group ${hasErrors(bean: thesisInstance, field: 'description', 'error')} required">
        <div class="small-msg pull-right">
            <i class="icon-info-sign"></i>
            <g:message code="field.markdown.label" />
        </div>
        <label class="control-label" for="thesis.description">
            <strong><g:message code="thesis.description.label"/></strong>
            <span class="required-indicator">*</span></strong>
        </label>
        <div class="controls">
            <g:textArea name="thesis.description" rows="15" value="${thesisInstance?.description}"/>
        </div>
    </div>
</sec:ifAnyGranted>

<div class="control-group">
    <label class="control-label" for="tag-list">
        <strong><g:message code="thesis.tags.label"/></strong>
    </label>
    <div class="controls">
        <g:select id="tag-list" name="thesis.tags.title" multiple="multiple" from="${thesisInstance?.tags}"
                  value="${thesisInstance?.tags}"/>
        <script type="text/javascript">$('#tag-list').taggy();</script>
    </div>
</div>
