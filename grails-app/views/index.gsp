<%@ page import="com.redhat.theses.util.Util" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title><g:message code="home.title"/></title>
</head>
<body>
    <div class="span8 content">

        <g:if test="${config?.announcement}">
            <div class="alert alert-error">
                <markdown:renderHtml text="${config?.announcement}" />
            </div>
        </g:if>

        <h2><g:message code="home.latest.topics.header"/></h2>
        <g:if test="${topicList && topicList.size() != 0}">
        <ul class="tms-list unstyled">
            <g:each in="${topicList}" var="topic">
            <li class="tms-elem">
                <i class="icon-book"></i>
                <div class="tms-elem-link">
                    <span class="pull-right">
                        <i class="icon-comment"></i> ${commentCounts[topic] ?: 0}
                    </span>
                    <g:link controller="topic"
                            action="show"
                            id="${topic?.id}"
                            params="[headline: Util.hyphenize(topic?.title)]"
                        ><g:fieldValue field="title" bean="${topic}"/></g:link>
                    <ul class="inline">
                        <li>
                            <i class="icon-time"></i>
                            <g:formatDate date="${topic?.dateCreated}"
                                          dateStyle="LONG"
                                          type="date" />
                        </li>
                    </ul>
                </div>
            </li>
            </g:each>
        </ul>
        </g:if>
        <g:else>
        <p class="muted"><g:message code="topic.no.topics.found"/></p>
        </g:else>
    </div>

    <div class="span4 sidebar">
        <div class="panel right">
            <h4><g:message code="home.your.theses.header"/></h4>
            <sec:ifLoggedIn>
                <g:if test="${yourTheses?.size() != 0}">
                    <div class="panel-content">
                    <g:each in="${yourTheses}" var="yourThesis">
                        <dl>
                            <dt class="tms-tooltip" data-placement="left"
                                data-original-title="${message(code: 'topic.label').toString()}">
                                <i class="icon-book"></i>
                            </dt>
                            <dd>
                                <g:link controller="topic" action="show"
                                        id="${yourThesis?.topicId}"
                                        params="[headline: Util.hyphenize(yourThesis?.topic?.title)]"
                                    >${yourThesis?.topic?.title}</g:link>
                            </dd>
                            <dt class="tms-tooltip" data-placement="left"
                                data-original-title="${message(code: 'role.supervisor.label').toString()}">
                                <i class="icon-user"></i>
                            </dt>
                            <dd>
                                <g:link controller="user" action="show"
                                        id="${yourThesis?.supervisorId}"
                                    ><g:fieldValue field="fullName" bean="${yourThesis?.supervisor}"/></g:link>
                                </dd>
                            <dt class="tms-tooltip" data-placement="left"
                                data-original-title="${message(code: 'thesis.status.label').toString()}">
                                <i class="icon-question-sign"></i>
                            </dt>
                            <dd>
                                <g:if test="${yourThesis?.status?.toString() != 'FINISHED'}">
                                    <g:message code="thesis.status.${yourThesis?.status?.toString()?.toLowerCase()}.label" />
                                </g:if>
                                <g:else>
                                    <g:message code="thesis.status.awarded.grade"
                                                args="[yourThesis?.grade]"/>
                                </g:else>
                            </dd>
                        </dl>
                    </g:each>
                    </div>
                </g:if>
                <g:else>
                    <div class="panel-content">
                        <g:message code="home.no.your.theses.found"/>
                    </div>
                </g:else>
            </sec:ifLoggedIn>
            <sec:ifNotLoggedIn>
                <div class="panel-content">
                    <p>
                        <g:message code="home.loggin.to.show.theses"
                                   args="[createLink(controller: 'login')]"/>
                    </p>
                </div>
            </sec:ifNotLoggedIn>
        </div>
    </div>
</body>
</html>
