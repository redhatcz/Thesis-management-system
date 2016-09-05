<sec:ifAnyGranted roles="ROLE_OWNER, ROLE_ADMIN, ROLE_SUPERVISOR">
    <div class="control-group ${hasErrors(bean: thesisInstance, field: 'title', 'error')} required">
        <div class="control-with-msg">
            <label class="control-label" for="thesis.title">
                <strong><g:message code="thesis.title.label"/></strong>
                <strong><span class="required-indicator">*</span></strong>
            </label>
        </div>
        <div class="controls half">
            <g:textField name="thesis.title" value="${thesisInstance?.title}" placeholder="${message(code: 'thesis.title.label')}" />
        </div>
        <div class="info">
            <g:message code="${message(code:'info.thesis.create.title')}"/>
        </div>
    </div>

    <div class="control-group ${hasErrors(bean: thesisInstance?.assignee, field: 'fullName', 'error')} required">
        <div class="control-with-msg">
            <label class="control-label" for="thesis.assignee.fullName">
                <strong><g:message code="thesis.assignee.label"/></strong>
                <strong><span class="required-indicator">*</span></strong>
            </label>
        </div>
        <div class="controls half">
            <g:hiddenField name="thesis.assignee.id" value="${thesisInstance?.assignee?.id}"/>
            <a4g:textField name="thesis.assignee.fullName"
                           value="${thesisInstance?.assignee?.fullName}"
                           disabled="${disabledAssigneeField}"
                           data-autocomplete-url="${createLink(controller: 'json', action: 'listUsersByName')}"
                           data-autocomplete-target="thesis.assignee.id"
                           placeholder="${message(code: 'thesis.assignee.label')}"/>
        </div>
        <div class="info">
            <g:message code="${message(code:'info.thesis.create.asignee')}"/>
        </div>
    </div>

    <div class="control-group ${hasErrors(bean: thesisInstance, field: 'university', 'error')} required">
        <div class="control-with-msg">
            <label class="control-label" for="thesis.university.id">
                <strong><g:message code="university.label"/></strong>
                <strong><span class="required-indicator">*</span></strong>
            </label>
        </div>
        <div class="controls half">
            <g:if test="${disabledUniversityField}">
                <g:hiddenField name="thesis.university.id" value="${thesisInstance?.university?.id}"/>
                <g:field type="text" name="thesis.university.name" readonly="readonly" value="${thesisInstance?.university?.name}"/>
            </g:if>
            <g:else>
                <g:select name="thesis.university.id"
                    from="${universityList}"
                    noSelection="['':message(code: 'no.selection.label')]"
                    optionKey="id"
                    value="${thesisInstance?.university?.id}" />
            </g:else>
        </div>
        <div class="info">
            <g:message code="${message(code:'info.thesis.create.university')}"/>
        </div>
    </div>

    <div class="control-group ${hasErrors(bean: thesisInstance, field: 'supervisor', 'error')}">
        <div class="control-with-msg">
            <label class="control-label" for="thesis.supervisor.fullName">
                <strong><g:message code="role.supervisor.label"/></strong>
            </label>
        </div>
        <div class="controls half">
            <g:if test="${supervisorsList}">
                <g:if test="${supervisorsList.size() == 1}">
                    <g:hiddenField name="thesis.supervisor.id" value="${supervisorsList[0].id}"/>
                    <g:field type="text" name="thesis.supervisor.name" readonly="readonly" value="${supervisorsList[0].fullName}"/>
                </g:if>
                <g:else>
                    <g:select name="thesis.supervisor.id"
                        from="${supervisorsList}"
                        optionKey="id"
                        value="${supervisorsList.get(0)?.id}" />
                </g:else>
            </g:if>
            <g:else>
                <g:hiddenField name="thesis.supervisor.id"
                               value="${thesisInstance?.supervisor?.id}"/>
                <g:hiddenField name="a4g-role[${i}]" value="${com.redhat.theses.auth.Role.SUPERVISOR}"/>
                <a4g:textField name="thesis.supervisor.fullName"
                               value="${thesisInstance?.supervisor?.fullName}"
                               data-autocomplete-url="${createLink(controller: 'json', action: 'listUsersByNameAndRole')}"
                               data-autocomplete-target="thesis.supervisor.id"
                               data-autocomplete-opts="a4g-role[${i}]@role"
                               placeholder="${message(code: 'role.supervisor.label')}"/>
            </g:else>
        </div>
        <div class="info">
            <g:message code="${message(code:'info.thesis.create.supervisor')}"/>
        </div>
    </div>

    <div class="control-group ${hasErrors(bean: thesisInstance, field: 'type', 'error')} required">
        <div class="control-with-msg">
            <label class="control-label" for="thesis.type">
                <strong><g:message code="thesis.type.label"/></strong>
                <strong><span class="required-indicator">*</span></strong>
            </label>
        </div>
        <div class="controls half">
            <g:select name="thesis.type"
                      from="${typeList}"
                      noSelection="['':message(code: 'no.selection.label')]"
                      optionValue="${{g.message(code:"thesis.type.${it?.toString()?.toLowerCase()}.label")}}"
                      value="${thesisInstance?.type?.toString()}"/>
        </div>
        <div class="info">
            <p><g:message code="${message(code:'info.thesis.create.type')}"/></p>
        </div>
    </div>

    <!-- If creating new thesis disable status and grade fields -->
    <g:if test="${thesisInstance?.id}">
        <div class="control-group ${hasErrors(bean: thesisInstance, field: 'status', 'error')}">
            <div class="control-with-msg">
                <label class="control-label" for="thesis.status">
                    <strong><g:message code="thesis.status.label"/></strong>
                </label>
            </div>
            <div class="controls">
                <g:select name="thesis.status"
                          from="${statusList}"
                          noSelection="['':message(code: 'no.selection.label')]"
                          optionValue="${{g.message(code:"thesis.status.${it?.toString()?.toLowerCase()}.label")}}"
                          value="${thesisInstance?.status?.toString()}"/>
            </div>
            <div class="info">
                <p><g:message code="${message(code:'info.thesis.create.status')}"/></p>
            </div>
        </div>

        <div class="control-group ${hasErrors(bean: thesisInstance, field: 'grade', 'error')}">
            <div class="control-with-msg">
                <label class="control-label" for="thesis.grade">
                    <strong><g:message code="thesis.grade.label"/></strong>
                </label>
            </div>
            <div class="controls">
                <g:select name="thesis.grade"
                          from="${gradeList}"
                          noSelection="['':message(code: 'no.selection.label')]"
                          value="${thesisInstance?.grade?.toString()}"/>
            </div>
            <div class="info">
                <p><g:message code="${message(code:'info.thesis.create.grade')}"/></p>
            </div>
        </div>
    </g:if>
</sec:ifAnyGranted>

<div class="control-group ${hasErrors(bean: thesisInstance, field: 'thesisAbstract', 'error')}">
    <div class="small-msg pull-right">
        <i class="icon-info-sign"
           title="${message(code:'info.thesis.create.thesisAbstract')}"></i>
    </div>
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
            <i class="icon-info-sign"
               title="${message(code:'info.thesis.create.description')}"></i>
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
    <div class="small-msg pull-right">
        <i class="icon-info-sign"
           title="${message(code:'info.thesis.create.links')}"></i>
    </div>
    <label class="control-label" for="thesis-links">
        <strong><g:message code="thesis.links.label"/></strong>
    </label>
    <div class="controls">
        <g:set var="links" value="${thesisInstance?.links ? [thesisInstance?.links].flatten() as List : [] as List}"/>
        <richg:dynamicField id="thesis-links"
                            for="${links}"
                            var="link"
                            index="i">
            <g:textField name="thesis.links"
                         id="thesis.links[${i}]"
                         value="${link}"/>
        </richg:dynamicField>
    </div>
</div>
<div class="control-group">
    <div class="small-msg pull-right">
        <i class="icon-info-sign"
           title="${message(code:'info.thesis.create.tags')}"></i>
    </div>
    <label class="control-label" for="thesis-tags">
        <strong><g:message code="thesis.tags.label"/></strong>
    </label>
    <div class="controls">
        <g:select id="thesis-tags" name="thesis.tags.title" multiple="multiple" from="${thesisInstance?.tags}"
                  value="${thesisInstance?.tags}"/>
        <script type="text/javascript">
        $(document).ready(function() {
            $('#thesis-tags').taggy();
            $('.taggy-tag-input').typeahead({
                source: function(term, process) {
                    $.get("${g.createLink(controller: 'json', action: 'listTagsByTitle')}", "term=" + term, function(data) {
                        process(data);
                    });
                },
                detailsType: 'none',
                minWidth: ''
            });
        });
        </script>
    </div>
</div>
