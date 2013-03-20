<div id="navigation">
    <ul>
        <li id="news-feed" class="${request.requestURI == request.contextPath + '/' ? 'active' : ''}">
            <g:link uri="/"><g:message code="navigation.home.button"/></g:link>
        </li>
        <li id="topics" class="${controllerName == 'topic' ? 'active' : ''}">
            <g:link controller="topic"><g:message code="navigation.topics.button"/></g:link>
        </li>
        <li id="theses" class="${controllerName == 'thesis' ? 'active' : ''}">
            <g:link controller="thesis"><g:message code="navigation.theses.button"/></g:link>
        </li>
        <li id="users" class="${controllerName == 'user' ? 'active' : ''}">
            <g:link controller="user"><g:message code="navigation.members.button"/></g:link>
        </li>
        <li id="universities" class="${controllerName == 'university' ? 'active' : ''}">
            <g:link controller="university"><g:message code="navigation.universities.button"/></g:link>
        </li>
        <sec:ifAnyGranted roles="ROLE_OWNER, ROLE_SUPERVISOR">
        <li id="applications" class="${controllerName == 'application' ? 'active' : ''}">
            <g:link controller="application"><g:message code="navigation.applications.button"/></g:link>
        </li>
        </sec:ifAnyGranted>
    </ul>
</div>
