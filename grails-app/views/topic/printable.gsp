<%@ page import="com.redhat.theses.util.Util; com.redhat.theses.Topic" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="printable">
    <title><g:message code="topic.printable.title" /></title>
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
        <g:hiddenField name="filter.tags.title" value="${params?.filter?.tags?.title}"/>
        <g:textField value="${params?.filter?.title}" class="wide"
                     name="filter.title" placeholder="${message(code: 'topic.title.label')}"/>
        <g:textField value="${params?.filter?.owner?.fullName}" class="wide"
                     name="filter.owner.fullName" placeholder="${message(code: 'role.owner.label')}"/>
        <g:textField value="${params?.filter?.supervisions?.supervisor?.fullName}" class="wide"
                     name="filter.supervisions.supervisor.fullName" placeholder="${message(code: 'role.supervisor.label')}"/>
        <g:select name="filter.universities.id" from="${universities}"
                  noSelection="['':message(code:'topic.university.select.label')]"
                  optionKey="id" value="${params?.filter?.universities?.id}"
                  class="many-to-one"/>
        <g:dateRangePicker id="dateCreated" name="filter.dateCreated" label="${message(code: 'topic.dateCreated.label')}"
                           placeholderMsg="datepicker" params="${params}" />         
        <label>
            <g:checkBox name="filter.onlyEnabled" value="${params?.filter?.onlyEnabled}"/> <g:message code="topic.show.only.enabled.label"/>
        </label>
        <label>
            <g:checkBox name="option.noSupervisors" value="${params?.option?.noSupervisors}"/> <g:message code="topic.show.only.no.supervisor"/>
        </label>
    </content>

    <content tag="options.view">
        <g:each var="property" in="${properties}">
            <label>
                <g:checkBox name="view.${property.key}" value="${params?.view?.getAt(property.key)}"
                    /> <g:message code="${property.value}.${property.key}.label"/>
            </label>
        </g:each>
        <g:if test="${universityView}">
            <label>
                <g:hiddenField name="viewingSupervisors" value="true"/>
                <g:checkBox name="view.supervisors" value="${params?.view?.supervisors}"
                    /> <g:message code="topic.supervisors.for" args="[university?.acronym]"/>
            </label>
        </g:if>
    </content>

    <content tag="table.head">
        <%--Only 3 properties are hardcoded and are not generated in the loop because of different message codes or order--%>

        <g:each var="property" in="${properties}">
            <g:if test="${params?.view?.getAt(property.key)}">
                <th><g:message code="${property.value}.${property.key}.label"/></th>
            </g:if>
        </g:each>
        <g:if test="${universityView && params?.view?.supervisors}">
            <th><g:message code="topic.supervisors.for" args="[university?.acronym]"/></th>
        </g:if>
    </content>

    <content tag="table.body">
        <g:each var="topic" in="${content}">
            <tr>
                <g:if test="${params?.view?.title}">
                    <td><g:link action="show" id="${topic?.id}" params="[headline: Util.hyphenize(topic?.title)]">
                        <g:fieldValue bean="${topic}" field="title"/>
                    </g:link></td>
                </g:if>

                <g:if test="${params?.view?.owner}">
                    <td><g:link controller="user" action="show" id="${topic?.owner?.id}">
                        <g:fieldValue bean="${topic?.owner}" field="fullName"/>
                    </g:link></td>
                </g:if>

                <g:if test="${params?.view?.secondaryTitle}">
                    <td>
                        <g:if test="${topic?.secondaryTitle}">
                            <g:fieldValue bean="${topic}" field="secondaryTitle"/>
                        </g:if>
                        <g:else>
                            <em><g:message code="no.result"/></em>
                        </g:else>
                    </td>
                </g:if>

                <g:if test="${params?.view?.dateCreated}">
                    <td><g:formatDate date="${topic?.dateCreated}" dateStyle="LONG" type="date" /></td>
                </g:if>

                <g:if test="${params?.view?.types}">
                    <td>
                    <g:if test="${topic?.types}">
                        <g:each in="${topic?.types}" var="type">
                            <g:message code="topic.type.${type?.toString()?.toLowerCase()}.short.label"
                            /><g:if test="${topic?.types?.last() != type}"><span>, </span></g:if>
                        </g:each>
                    </g:if>
                    <g:else>
                        <em><g:message code="no.result"/></em>
                    </g:else>
                    </td>
                </g:if>

                <g:if test="${params?.view?.categories}">
                    <td>
                    <g:if test="${topic?.categories}">
                        <g:each in="${topic?.categories}" var="category">
                            <g:fieldValue bean="${category}" field="title"
                                /><g:if test="${topic?.categories?.last() != category}"><span>, </span></g:if>
                        </g:each>
                    </g:if>
                    <g:else>
                        <em><g:message code="no.result"/></em>
                    </g:else>
                    </td>
                </g:if>

                <g:if test="${universityView && params?.view?.supervisors}">
                    <td>
                    <g:if test="${topicsWithSupervisors[topic]}">
                        <g:each in="${topicsWithSupervisors[topic]}" var="supervisor">
                            <g:link controller="user" action="show" id="${supervisor?.id}">
                                <g:fieldValue bean="${supervisor}" field="fullName"
                                /></g:link><g:if test="${topicsWithSupervisors[topic]?.last() != supervisor}"><span>, </span></g:if>
                        </g:each>
                    </g:if>
                    <g:else>
                        <em><g:message code="no.result"/></em>
                    </g:else>
                    </td>
                </g:if>

                <g:if test="${params?.view?.tags}">
                    <td>
                        <g:if test="${topic?.tags}">
                            <g:each in="${topic?.tags}" var="tag">
                                <g:fieldValue bean="${tag}" field="title"
                                /><g:if test="${topic?.tags?.last() != tag}"><span>, </span></g:if>
                            </g:each>
                        </g:if>
                        <g:else>
                            <em><g:message code="no.result"/></em>
                        </g:else>
                    </td>
                </g:if>

                <g:if test="${params?.view?.lead}">
                    <td><g:fieldValue bean="${topic}" field="lead"/></td>
                </g:if>

                <g:if test="${params?.view?.description}">
                    <td><markdown:renderHtml text="${topic?.description}"/></td>
                </g:if>

                <g:if test="${params?.view?.secondaryDescription}">
                    <td>
                        <g:if test="${topic?.secondaryDescription}">
                            <markdown:renderHtml text="${topic?.secondaryDescription}"/>
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
