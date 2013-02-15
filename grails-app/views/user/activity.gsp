<%@ page import="com.redhat.theses.util.Util; com.redhat.theses.auth.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="user/showLayout"/>
    <title>Show user: activity</title>
</head>
<body>
    <content tag="active-button">activity</content>

    <content tag="main-box">
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
    </content>
</body>
</html>