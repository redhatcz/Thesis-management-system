<%@ page import="com.redhat.theses.util.Util" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title><g:message code="search.title" args="[params.q?.encodeAsHTML()]"/></title>
</head>
<body>
<div class="span8 content">
    <g:if test="${searchResult?.results && searchResult?.results?.size() != null}">
        <g:each var="result" in="${searchResult?.results}">
            <g:set var="resultClass" value="${result?.getClass()?.getSimpleName()?.toLowerCase()}"/>

            <richg:ifResourceExists template="${resultClass}Result">
                <g:render template="${resultClass}Result" model="[(resultClass): result]"/>
            </richg:ifResourceExists>
        </g:each>
    </g:if>
    <g:else>
        <p class="center muted"><g:message code="search.no.results.found"/></p>
    </g:else>

    <g:if test="${Util.isPaginationVisible(searchResult?.total, params.max)}">
        <g:paginate action="index" params="[q: params.q]" total="${searchResult?.total}" class="pagination-centered"/>
    </g:if>
</div>

<div class="span4 sidebar">
    <div class="panel right">

        <h4><g:message code="search.label"/></h4>
        <div class="panel-content">
            <g:form method="get">
                <g:textField class="wide" value="${params.q}" name="q" placeholder="${message(code: 'search.label')}"/>
                <button type="submit" class="tms-btn pull-right">
                    <i class="icon-search"></i>
                    <g:message code="search.button"/>
                </button>
            </g:form>
        </div>
    </div>
</div>
</body>
</html>