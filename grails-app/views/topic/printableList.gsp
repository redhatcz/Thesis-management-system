<%@ page import="com.redhat.theses.util.Util; com.redhat.theses.Topic" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="printable">
    <title><g:message code="topic.printable-list.title" /></title>
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
    <div id="options" class="no-print">
        <div class="fixed-options">
            <i class="icon-cog"></i>
            <a href="#options-modal" class="no-print" data-toggle="modal">
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

                    <h4><g:message code="view.options.header"/></h4>
                    <p><b><g:message code="view.options.table.width"/>:</b></p>
                    <label>
                        <g:radio value="on" name="fixedTable" checked="${params?.fixedTable != 'off' ? 'true' : ''}"
                            /> <g:message code="view.options.table.fixed"/>
                    </label>
                    <label>
                        <g:radio value="off" name="fixedTable" checked="${params?.fixedTable == 'off' ? 'true' : ''}"
                            /> <g:message code="view.options.table.manual"
                            />: <g:textField name="manualTableWidth" style="width: 70px; height: 20px;" value="${params?.manualTableWidth}"/> px
                    </label>

                    <p><b><g:message code="view.options.fields"/>:</b></p>
                    <g:hiddenField name="viewing" value="true"/>
                    <label>
                        <g:checkBox name="view.title" value="${params?.view?.title}"
                            /> <g:message code="topic.title.label"/>
                    </label>
                    <label>
                        <g:checkBox name="view.secondaryTitle" value="${params?.view?.secondaryTitle}"
                            /> <g:message code="topic.secondaryTitle.label"/>
                    </label>
                    <label>
                        <g:checkBox name="view.dateCreated" value="${params?.view?.dateCreated}"
                            /> <g:message code="topic.dateCreated.label"/>
                    </label>
                    <label>
                        <g:checkBox name="view.owner" value="${params?.view?.owner}"
                            /> <g:message code="role.owner.label"/>
                    </label>
                    <label>
                        <g:checkBox name="view.types" value="${params?.view?.types}"
                            /> <g:message code="topic.types.label"/>
                    </label>
                    <label>
                        <g:checkBox name="view.categories" value="${params?.view?.categories}"
                            /> <g:message code="topic.categories.label"/>
                    </label>
                    <g:if test="${universityView}">
                    <label>
                        <g:hiddenField name="viewingSupervisors" value="true"/>
                        <g:checkBox name="view.supervisors" value="${params?.view?.supervisors}"
                            /> <g:message code="topic.supervisors.for" args="[university?.acronym]"/>
                    </label>
                    </g:if>
                    <label>
                        <g:checkBox name="view.tags" value="${params?.view?.tags}"
                            /> <g:message code="topic.tags.label"/>
                    </label>
                    <label>
                        <g:checkBox name="view.lead" value="${params?.view?.lead}"
                            /> <g:message code="topic.lead.label"/>
                    </label>
                    <label>
                        <g:checkBox name="view.description" value="${params?.view?.description}"
                            /> <g:message code="topic.description.label"/>
                    </label>
                    <label>
                        <g:checkBox name="view.secondaryDescription" value="${params?.view?.secondaryDescription}"
                            /> <g:message code="topic.secondaryDescription.label"/>
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
    <table id="printable-table-topics" class="table table-hover"
           style="${params?.fixedTable == 'off' ? "width: ${params?.manualTableWidth?.encodeAsHTML()}px;" : ''}">
        <thead>
            <tr>
                <g:if test="${params?.view?.title}">
                    <th><g:message code="topic.title.label"/></th>
                </g:if>

                <g:if test="${params?.view?.secondaryTitle}">
                    <th><g:message code="topic.secondaryTitle.label"/></th>
                </g:if>

                <g:if test="${params?.view?.dateCreated}">
                    <th><g:message code="topic.dateCreated.label"/></th>
                </g:if>

                <g:if test="${params?.view?.owner}">
                    <th><g:message code="role.owner.label"/></th>
                </g:if>

                <g:if test="${params?.view?.types}">
                    <th><g:message code="topic.types.label"/></th>
                </g:if>

                <g:if test="${params?.view?.categories}">
                    <th><g:message code="topic.categories.label"/></th>
                </g:if>

                <g:if test="${universityView && params?.view?.supervisors}">
                    <th><g:message code="topic.supervisors.for" args="[university?.acronym]"/></th>
                </g:if>

                <g:if test="${params?.view?.tags}">
                    <th><g:message code="topic.tags.label"/></th>
                </g:if>

                <g:if test="${params?.view?.lead}">
                    <th><g:message code="topic.lead.label"/></th>
                </g:if>

                <g:if test="${params?.view?.description}">
                    <th><g:message code="topic.description.label"/></th>
                </g:if>

                <g:if test="${params?.view?.secondaryDescription}">
                    <th><g:message code="topic.secondaryDescription.label"/></th>
                </g:if>
            </tr>
        </thead>
        <tbody>
        <g:each var="topic" in="${topics}">
            <tr>
                <g:if test="${params?.view?.title}">
                    <td><g:link action="show" id="${topic?.id}" params="[headline: Util.hyphenize(topic?.title)]">
                        <g:fieldValue bean="${topic}" field="title"/>
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

                <g:if test="${params?.view?.owner}">
                    <td><g:link controller="user" action="show" id="${topic?.owner?.id}">
                        <g:fieldValue bean="${topic?.owner}" field="fullName"/>
                    </g:link></td>
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
    </tbody>
    </table>
    </g:if>
    <g:else>
        <p><g:message code="topic.no.topics.found"/></p>
    </g:else>
</body>
</html>
