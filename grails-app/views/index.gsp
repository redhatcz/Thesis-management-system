<%@ page import="com.redhat.theses.util.Util" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title><g:message code="home.title"/></title>
</head>
<body>
    <div class="span8 content">
        <h2><g:message code="home.latest.topics.header"/></h2>
        <g:each in="${topicList}" var="topic">
            <div class="table-layout">
                <g:link controller="topic" action="show" id="${topic?.id}" params="${Util.hyphenize(topic?.title)}"><g:fieldValue field="title" bean="${topic}"/></g:link>
            </div>
        </g:each>

        <h2>Statistics</h2>
        <span>All topics: ${statistics?.topicCount}</span><br/>
        <span>All theses: ${statistics?.thesisCount}</span><br/>
        <span>Successfully finished theses: ${statistics?.thesisSuccessfulCount}</span><br/>
        <span>Members registered: ${statistics?.userCount}</span>
    </div>

    <div class="span4 sidebar">
        <div class="panel right">
            <h4><g:message code="home.your.theses.header"/></h4>
            <sec:ifLoggedIn>
                <g:if test="${yourTheses?.size() != 0}">
                    <div class="panel-content">
                    <g:each in="${yourTheses}" var="yourThesis">
                        <dl>
                            <dt><i class="icon-book"></i> ${message(code: 'topic.label').toString().toLowerCase()}</dt>
                            <dd>
                                <g:link controller="topic" action="show" id="${yourThesis?.topicId}"
                                        params="[headline: Util.hyphenize(yourThesis?.topic?.title)]">${yourThesis?.topic?.title}</g:link>
                            </dd>
                            <dt><i class="icon-user"></i> ${message(code: 'thesis.supervisor.label').toString().toLowerCase()}</dt>
                            <dd><g:link controller="user" action="show" id="${yourThesis?.supervisorId}"><g:fieldValue field="fullName" bean="${yourThesis?.supervisor}"/></g:link></dd>
                            <dt><i class="icon-question-sign"></i> ${message(code: 'thesis.status.label').toString().toLowerCase()}</dt>
                            <dd>
                                <g:if test="${yourThesis?.status?.toString() != 'FINISHED'}">
                                    <g:message code="thesis.status.${yourThesis?.status?.toString()?.toLowerCase()}.label" />
                                </g:if>
                                <g:else>
                                    <g:message code="thesis.status.awarded.grade" args="[yourThesis?.grade]"/>
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
