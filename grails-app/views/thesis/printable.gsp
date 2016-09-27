<%@ page import="com.redhat.theses.util.Util; com.redhat.theses.Thesis;  com.redhat.theses.Status;  com.redhat.theses.University" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="printable">
    <title><g:message code="thesis.printable.title" /></title>
    <style type="text/css">
    @media print
    {
        .no-print, .no-print *
        {
            display: none !important;
        }
        a {
            text-decoration: none;
        }
        table {
            border-width: 1px;
        }
    }
    </style>
</head>
<body>
    <content tag="options.filter">
        <g:hiddenField name="filter.categories.id" value="${params?.filter?.categories?.id}"/>
        <g:textField value="${params?.filter?.title}" class="wide"
                     name="filter.title" placeholder="${message(code: 'thesis.title.label')}"/>
        <g:textField value="${params?.filter?.topic?.title}" class="wide"
                     name="filter.topic.title" placeholder="${message(code: 'thesis.topic.label')}"/>
        <g:textField value="${params?.filter?.assignee?.fullName}" class="wide"
                     name="filter.assignee.fullName" placeholder="${message(code: 'thesis.assignee.label')}"/>
        <g:textField value="${params?.filter?.owner?.fullName}" class="wide"
                     name="filter.owner.fullName" placeholder="${message(code: 'role.owner.label')}"/>
        <g:textField value="${params?.filter?.supervisor?.fullName}" class="wide"
                     name="filter.supervisor.fullName" placeholder="${message(code: 'role.supervisor.label')}"/>
        <g:select name="filter.university.id" from="${universities}"
                  noSelection="['':message(code:'topic.university.select.label')]"
                  optionKey="id" value="${params?.filter?.universities?.id}"
                  class="many-to-one"/>
        <g:select name="filter.status"
                  from="${statuses}"
                  noSelection="['':message(code: 'thesis.status.select.label')]"
                  optionValue="${{g.message(code: "thesis.status.${it?.toString()?.toLowerCase()}.label")}}"
                  value="${params?.filter?.status}"/>
        <g:select name="filter.grade"
                  from="${grades}"
                  noSelection="['':message(code: 'thesis.grade.select.label')]"
                  optionValue="${it?.toString()}"
                  value="${params?.filter?.grade}"/>
        <g:select name="filter.type"
                  from="${types}"
                  noSelection="['':message(code: 'thesis.type.select.label')]"
                  optionValue="${{g.message(code: "thesis.type.${it?.toString()?.toLowerCase()}.label")}}"
                  value="${params?.filter?.type}"/>                            
        <g:dateRangePicker id="dateCreated" name="filter.dateCreated" label="${message(code: 'topic.dateCreated.label')}"
                           placeholderMsg="datepicker" params="${params}" />
        <g:dateRangePicker id="dateFinished" name="filter.dateFinished" label="${message(code: 'thesis.dateFinished.label')}"
                           placeholderMsg="datepicker" params="${params}" />
    </content>

    <content tag="options.view">
        <g:each var="property" in="${properties}">
            <label>
                <g:checkBox name="view.${property.key}" value="${params?.view?.getAt(property.key)}"
                    /> <g:message code="${property.value}.${property.key}.label"/>
            </label>
        </g:each>
    </content>

    <content tag="table.head">
        <g:each var="property" in="${properties}">
            <g:if test="${params?.view?.getAt(property.key)}">
                <th><g:message code="${property.value}.${property.key}.label"/></th>
            </g:if>
        </g:each>
    </content>

    <content tag="table.body">
        <g:each var="thesis" in="${content}">
            <tr>
                <g:if test="${params?.view?.title}">
                    <td><g:link action="show" id="${thesis?.id}" params="[headline: Util.hyphenize(thesis?.title)]">
                        <g:fieldValue bean="${thesis}" field="title"/>
                    </g:link></td>
                </g:if>

                <g:if test="${params?.view?.assignee}">
                    <td><g:link controller="user" action="show" id="${thesis?.assignee?.id}">
                        <g:fieldValue bean="${thesis?.assignee}" field="fullName"/>
                    </g:link></td>
                </g:if>

                <g:if test="${params?.view?.university}">
                    <td><g:link controller="university" action="show" id="${thesis?.university?.id}">
                        <g:fieldValue bean="${thesis?.university}" field="name"/>
                    </g:link></td>
                </g:if>

                <g:if test="${params?.view?.topic}">
                    <td><g:link controller="topic" action="show" id="${thesis?.topic?.id}">
                        <g:fieldValue bean="${thesis?.topic}" field="title"/>
                    </g:link></td>
                </g:if>

                <g:if test="${params?.view?.supervisor}">
                    <td><g:link controller="user" action="show" id="${thesis?.supervisor?.id}">
                        <g:fieldValue bean="${thesis?.supervisor}" field="fullName"/>
                    </g:link></td>
                </g:if>

                <g:if test="${params?.view?.owner}">
                    <td><g:link controller="user" action="show" id="${thesis?.topic?.owner?.id}">
                        <g:fieldValue bean="${thesis?.topic?.owner}" field="fullName"/>
                    </g:link></td>
                </g:if>

                <g:if test="${params?.view?.dateCreated}">
                    <td><g:formatDate date="${thesis?.dateCreated}" dateStyle="LONG" type="date" /></td>
                </g:if>

                <g:if test="${params?.view?.dateFinished}">
                    <td>
                        <g:if test="${thesis?.dateFinished}">
                            <g:formatDate date="${thesis?.dateFinished}" dateStyle="LONG" type="date" />
                        </g:if>
                        <g:else>
                            ---
                        </g:else>
                    </td>
                </g:if>

                <g:if test="${params?.view?.type}">
                    <td>
                        <g:message code="thesis.type.${thesis?.type?.toString()?.toLowerCase()}.label"/>
                    </td>
                </g:if>

                <g:if test="${params?.view?.status}">
                    <td>
                        <g:message code="thesis.status.${thesis?.status?.toString()?.toLowerCase()}.label"/>
                    </td>
                </g:if>

                <g:if test="${params?.view?.grade}">
                    <td>
                        <g:if test="${thesis?.status == Status.FINISHED }">
                            <g:fieldValue bean="${thesis}" field="grade"/>
                        </g:if>
                        <g:else>
                            ---
                        </g:else>
                    </td>
                </g:if>

                <g:if test="${params?.view?.tags}">
                    <td>
                        <g:if test="${thesis?.tags}">
                            <g:each in="${thesis?.tags}" var="tag">
                                <g:fieldValue bean="${tag}" field="title"
                                /><g:if test="${thesis?.tags?.last() != tag}"><span>, </span></g:if>
                            </g:each>
                        </g:if>
                        <g:else>
                            <em><g:message code="no.result"/></em>
                        </g:else>
                    </td>
                </g:if>

                <g:if test="${params?.view?.thesisAbstract}">
                    <td><g:fieldValue bean="${thesis}" field="thesisAbstract"/></td>
                </g:if>

                <g:if test="${params?.view?.description}">
                    <td><markdown:renderHtml text="${thesis?.description}"/></td>
                </g:if>

                <g:if test="${params?.view?.notes}">
                    <td>
                        <g:if test="${topic?.notest}">
                            <markdown:renderHtml text="${thesis?.notes}"/>
                        </g:if>
                        <g:else>
                            <em><g:message code="no.result"/></em>
                        </g:else>
                    </td>
                </g:if>
            </tr>
        </g:each>
    </content>
</body>
</html>
