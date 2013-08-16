<%@ page import="com.redhat.theses.util.Util" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="user/showLayout"/>
    <title><g:message code="user.show.title" args="[userInstance.fullName]"/></title>
</head>
<body>
<content tag="active-button">main</content>

<content tag="main-box">
    <g:if test="${ownedTopics}">
        <div class="tms-table">
            <table class="table">
                <thead>
                <tr>
                    <th><g:message code="user.led.topics"/></th>
                </tr>
                </thead>
                <tbody>
                <g:each in="${ownedTopics}" status="i" var="topicInstance">
                    <tr>
                        <td>
                            <g:link controller="topic" action="show" id="${topicInstance.id}"
                                    params="[headline: Util.hyphenize(topicInstance?.title)]"
                            ><g:fieldValue field="title" bean="${topicInstance}"/></g:link>
                        </td>
                    </tr>
                </g:each>
                </tbody>
                <g:if test="${ownedTopics?.size() >= 5}">
                <tfoot>
                    <tr>
                        <td>
                            <i class="icon-reorder"></i>
                            <g:link controller="topic" action="list"
                                    params="[filtering: true, 'filter.owner.id': userInstance?.id]">
                                <g:message code="more.button"/>
                            </g:link>
                        </td>
                    </tr>
                </tfoot>
                </g:if>
            </table>
        </div>
    </g:if>
    <g:if test="${supervisedTopics}">
        <div class="tms-table">
            <table class="table">
                <thead>
                <tr>
                    <th><g:message code="user.supervised.topics"/></th>
                </tr>
                </thead>
                <tbody>
                <g:each in="${supervisedTopics}" status="i" var="topicInstance">
                    <tr>
                        <td>
                            <g:link controller="topic" action="show" id="${topicInstance.id}"
                                    params="[headline: Util.hyphenize(topicInstance?.title)]"
                            ><g:fieldValue field="title" bean="${topicInstance}"/></g:link>
                        </td>
                    </tr>
                </g:each>
                </tbody>
                <g:if test="${supervisedTopics?.size() >= 5}">
                    <tfoot>
                    <tr>
                        <td>
                            <i class="icon-reorder"></i>
                            <g:link controller="topic" action="list"
                                    params="[filtering: true, 'filter.supervisions.supervisor.id': userInstance?.id]">
                                <g:message code="more.button"/>
                            </g:link>
                        </td>
                    </tr>
                    </tfoot>
                </g:if>
            </table>
        </div>
    </g:if>
    <g:if test="${supervisedTheses}">
        <div class="tms-table">
            <table class="table">
                <thead>
                <tr>
                    <th><g:message code="user.supervised.theses"/></th>
                </tr>
                </thead>
                <tbody>
                <g:each in="${supervisedTheses}" status="i" var="thesisInstance">
                    <tr>
                        <td>
                            <g:link controller="thesis" action="show" id="${thesisInstance.id}"
                                    params="[headline: Util.hyphenize(thesisInstance?.title)]"
                            ><g:fieldValue field="title" bean="${thesisInstance}"/></g:link>
                        </td>
                    </tr>
                </g:each>
                </tbody>
                <g:if test="${supervisedTheses?.size() >= 5}">
                <tfoot>
                    <tr>
                        <td>
                            <i class="icon-reorder"></i>
                            <g:link controller="thesis" action="list"
                                    params="[filtering: true, 'filter.supervisor.id': userInstance?.id]">
                                <g:message code="more.button"/>
                            </g:link>
                        </td>
                    </tr>
                </tfoot>
                </g:if>
            </table>
        </div>
    </g:if>
    <g:if test="${assignedTheses}">
    <div class="tms-table">
        <table class="table">
            <thead>
            <tr>
                <th><g:message code="user.assigned.theses"/></th>
                <th><g:message code="thesis.status.label"/></th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${assignedTheses}" status="i" var="thesisInstance">
                <tr>
                    <td>
                        <g:link controller="thesis" action="show" id="${thesisInstance.id}"
                                params="[headline: Util.hyphenize(thesisInstance?.title)]"
                            ><g:fieldValue field="title" bean="${thesisInstance}"/></g:link>
                    </td>
                    <td>
                        <g:if test="${thesisInstance?.status?.toString() != 'FINISHED'}">
                            <g:message code="thesis.status.${thesisInstance?.status?.toString()?.toLowerCase()}.label" />
                        </g:if>
                        <g:else>
                            <g:fieldValue bean="${thesisInstance}" field="grade"/>
                        </g:else>
                    </td>
                </tr>
            </g:each>
            </tbody>
            <g:if test="${assignedTheses?.size() >= 5}">
            <tfoot>
                <tr>
                    <td colspan="2">
                        <i class="icon-reorder"></i>
                        <g:link controller="thesis" action="list"
                                params="[filtering: true, 'filter.assignee.id': userInstance?.id]">
                            <g:message code="more.button"/>
                        </g:link>
                    </td>
                </tr>
            </tfoot>
            </g:if>
        </table>
    </div>
    </g:if>

    <g:if test="${!ownedTopics && !supervisedTheses && !assignedTheses}">
    <div class="well well-large">
        <g:message code="user.no.theses.found"/>
    </div>
    </g:if>
</content>
</body>
</html>
