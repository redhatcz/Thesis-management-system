<%@ page import="com.redhat.theses.util.Util" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Thesis management system</title>
</head>
<body>
    <div class="span8">
        <g:each in="${feedList}" var="feed">
            <div class="table-layout">
                <g:message code="${feed?.messageCode}" args="${feed?.args}"/>
            </div>
        </g:each>
        <g:if test="${Util.isPaginationVisible(feedListTotal, params?.max)}">
            <ul class="pager">
                <g:if test="${!Util.isLastPage(feedListTotal, params?.max, params?.offset)}">
                    <li class="next">
                        <g:link params="[max: Util.max(params?.max), offset: Util.nextOffset(params?.offset, params?.max)]">Older &rarr;</g:link>
                    </li>
                </g:if>
                <g:if test="${!Util.isFirstPage(params?.offset)}">
                    <li class="previous">
                        <g:link params="[max: Util.max(params?.max), offset: Util.previousOffset(params?.offset, params?.max)]">&larr; Newer</g:link>
                    </li>
                </g:if>
            </ul>
        </g:if>
    </div>

    <div class="span4">
        <div class="panel right">
            <h4>Your Theses</h4>
            <sec:ifLoggedIn>
                <g:if test="${yourTheses?.size() != 0}">
                    <div class="panel-content">
                    <g:each in="${yourTheses}" var="yourThesis">
                        <dl>
                            <dt><span class="entypo-thesis mini"></span>topic</dt>
                            <dd>
                                <g:link controller="topic" action="show" id="${yourThesis?.topicId}"
                                        params="[headline: Util.hyphenize(yourThesis?.topic?.title)]">${yourThesis?.topic?.title}</g:link>
                            </dd>
                            <dt><span class="entypo-user mini"></span>suprevisor</dt>
                            <dd><g:fieldValue field="fullName" bean="${yourThesis?.supervisor}"/></dd>
                            <dt><span class="entypo-status mini"></span>status</dt>
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
