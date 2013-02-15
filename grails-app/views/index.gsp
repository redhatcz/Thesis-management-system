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
        <div class="thesis">
            <h4>Your Thesis</h4>
            <dl>
                <dt>name</dt>
                <dd><a href="#">Java Security Centralization</a></dd>
                <dt>suprevisor</dt>
                <dd>RNDr. Robb Stark</dd>
                <dt>status</dt>
                <dd>The thesis has been awarded the grade <strong>A</strong></dd>
            </dl>
        </div>

        <!-- <ul class="nav nav-tabs nav-stacked">
            <g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName } }">
                <li class="controller">
                    <g:link controller="${c.logicalPropertyName}">${c.name}</g:link>
                </li>
            </g:each>
        </ul> -->
    </div>
</body>
</html>
