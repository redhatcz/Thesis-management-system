<%@ page import="com.redhat.theses.util.Util; com.redhat.theses.Topic" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="printable">
    <title><g:message code="topic.printable-list.title" /></title>
</head>
<body>
    <div id="options" class="no-print">
        <div class="fixed-options">
            <i class="icon-cog"></i>
            <a href="#options-modal" class="center" data-toggle="modal">
                <g:message code="options.button"/>
            </a>
        </div>

        <div id="options-modal" class="modal hide fade">
            <div class="modal-wrapper">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h3><g:message code="options.header"/></h3>
                </div>
                <g:form method="get" class="filter" action="printableList" style="margin: 0;">
                <div class="modal-body">
                    <h4><g:message code="filter.options.header"/></h4>
                    <g:hiddenField name="filtering" value="true"/>
                    <g:hiddenField name="filter.categories.id" value="${params?.filter?.categories?.id}"/>
                    <g:hiddenField name="filter.tags.title" value="${params?.filter?.tags?.title}"/>
                    <g:textField value="${params?.filter?.title}" class="wide"
                                 name="filter.title" placeholder="${message(code: 'topic.title.label')}"/>
                    <g:hiddenField name="type.title" value="ilike"/>
                    <g:textField value="${params?.filter?.owner?.fullName}" class="wide"
                                 name="filter.owner.fullName" placeholder="${message(code: 'role.owner.label')}"/>
                    <g:hiddenField name="type.owner.fullName" value="ilike"/>
                    <g:textField value="${params?.filter?.supervisions?.supervisor?.fullName}" class="wide"
                                 name="filter.supervisions.supervisor.fullName" placeholder="${message(code: 'role.supervisor.label')}"/>
                    <g:hiddenField name="type.supervisions.supervisor.fullName" value="ilike"/>
                    <g:select name="filter.universities.id" from="${universities}"
                              noSelection="['':message(code:'topic.university.select.label')]"
                              optionKey="id" value="${params?.filter?.universities?.id}"
                              class="many-to-one"/>
                    <label>
                        <g:checkBox name="filter.onlyEnabled" value="${params?.filter?.onlyEnabled}"/> <g:message code="topic.show.only.enabled.label"/>
                    </label>
                    <label>
                        <g:checkBox name="option.noSupervisors" value="${params?.option?.noSupervisors}"/> Show only topics with no supervisors for selected university
                    </label>
                </div>
                <div class="modal-footer">
                    <g:submitButton class="tms-btn pull-right" name="filter-button"
                                    value="${message(code: 'apply.button')}"/>
                </div>
                </g:form>
            </div>
        </div>
    </div>
    <g:if test="${topics}">
    <table id="printable-table-topics" class="table table-hover">
        <thead>
            <tr>
                <th><g:message code="id.label"/></th>
                <th><g:message code="topic.title.label"/></th>
                <th><g:message code="role.owner.label"/></th>
                <th><g:message code="topic.dateCreated.label"/></th>
                <th><g:message code="topic.categories.label"/></th>
                <g:if test="${universityView}">
                <th><g:message code="topic.supervisors.for" args="[university?.acronym]"/></th>
                </g:if>
            </tr>
        </thead>
        <tbody>
        <g:each var="topic" in="${topics}">
            <tr>
                <td><g:link action="show" id="${topic?.id}" params="[headline: Util.hyphenize(topic?.title)]">
                    <g:fieldValue bean="${topic}" field="id"/>
                </g:link></td>
                <td><g:fieldValue bean="${topic}" field="title"/></td>
                <td><g:fieldValue bean="${topic?.owner}" field="fullName"/></td>
                <td><g:formatDate date="${topic?.dateCreated}"
                                  dateStyle="LONG"
                                  type="date" /></td>
                <td>
                <g:if test="${topic?.categories}">
                    <g:each in="${topic?.categories}" var="category">
                        <g:if test="${topic?.categories?.last() != category}">
                            <g:fieldValue bean="${category}" field="title"/><span>, </span>
                        </g:if>
                        <g:else>
                            <g:fieldValue bean="${category}" field="title"/>
                        </g:else>
                    </g:each>
                </g:if>
                <g:else>
                    <em><g:message code="no.result"/></em>
                </g:else>
                </td>
                <g:if test="${universityView}">
                <td>
                <g:if test="${topicsWithSupervisors[topic]}">
                    <g:each in="${topicsWithSupervisors[topic]}" var="supervisor">
                        <g:if test="${topicsWithSupervisors[topic]?.last() != supervisor}">
                            <g:fieldValue bean="${supervisor}" field="fullName"/><span>, </span>
                        </g:if>
                        <g:else>
                            <g:fieldValue bean="${supervisor}" field="fullName"/>
                        </g:else>
                    </g:each>
                </g:if>
                <g:else>
                    <em><g:message code="no.result"/></em>
                </g:else>
                </td>
                </g:if>
            </tr>
        </g:each>
    </tbody>
    </table>
    </g:if>
    <g:else>
        <p><g:message code="topic.no.topics.found"/></p>
    </g:else>
</body>
</html>
