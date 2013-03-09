<%@ page import="com.redhat.theses.util.Util" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Thesis management system</title>
</head>
<body>
    <div class="span8 content">
        <h3>Latest topics</h3>
        <g:each in="${topicList}" var="topic">
            <div class="table-layout">
                <g:link controller="topic" action="show" id="${topic?.id}" params="${Util.hyphenize(topic?.title)}"><g:fieldValue field="title" bean="${topic}"/></g:link>
            </div>
        </g:each>

        <h3>Statistics</h3>
        <span>All topics: ${statistics?.topicCount}</span><br/>
        <span>All theses: ${statistics?.thesisCount}</span><br/>
        <span>Successfully finished theses: ${statistics?.thesisSuccessfulCount}</span><br/>
        <span>Members registered: ${statistics?.userCount}</span>
    </div>

    <div class="span4 sidebar">
        <div class="panel right">
            <h4>Your Theses</h4>
            <sec:ifLoggedIn>
                <g:if test="${yourTheses?.size() != 0}">
                    <div class="panel-content">
                    <g:each in="${yourTheses}" var="yourThesis">
                        <dl>
                            <dt><i class="icon-book"></i> topic</dt>
                            <dd>
                                <g:link controller="topic" action="show" id="${yourThesis?.topicId}"
                                        params="[headline: Util.hyphenize(yourThesis?.topic?.title)]">${yourThesis?.topic?.title}</g:link>
                            </dd>
                            <dt><i class="icon-user"></i> suprevisor</dt>
                            <dd><g:fieldValue field="fullName" bean="${yourThesis?.supervisor}"/></dd>
                            <dt><i class="icon-question-sign"></i> status</dt>
                            <dd>
                                <g:if test="${yourThesis?.status?.toString() != 'FINISHED'}">
                                    <g:message code="thesis.status.${yourThesis?.status?.toString()?.toLowerCase()}.label" />
                                </g:if>
                                <g:else>
                                    This has been awarded the grade <strong>${yourThesis?.grade}</strong>.
                                </g:else>
                            </dd>
                        </dl>
                    </g:each>
                    </div>
                </g:if>
                <g:else>
                    <div class="panel-content">You have currently no theses.</div>
                </g:else>
            </sec:ifLoggedIn>
            <sec:ifNotLoggedIn>
                <div class="panel-content">You must be <g:link controller="login">logged in</g:link> to view your theses.</div>
            </sec:ifNotLoggedIn>
        </div>
    </div>
</body>
</html>
