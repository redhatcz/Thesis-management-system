<ul class="nav nav-pills">
    <li id="news-feed" class="${request.requestURI == request.contextPath + '/' ? 'active' : ''}">
        <g:link uri="/">News Feed</g:link>
    </li>
    <li id="topics" class="${controllerName == 'topic' ? 'active' : ''}">
        <g:link controller="topic">Topics</g:link>
    </li>
    <li id="theses" class="${controllerName == 'thesis' ? 'active' : ''}">
        <g:link controller="thesis">Theses</g:link>
    </li>
    <li id="users" class="${controllerName == 'user' ? 'active' : ''}">
        <g:link controller="user">Members</g:link>
    </li>
    %{--<sec:ifAllGranted roles="ROLE_OWNER">--}%
        %{--<li id="applications" class="${controllerName == 'application' ? 'active' : ''}">--}%
            %{--<g:link controller="application">Applications</g:link>--}%
        %{--</li>--}%
    %{--</sec:ifAllGranted>--}%
    %{--<sec:ifAllGranted roles="ROLE_ADMIN">--}%
        %{--<li id="universities" class="${controllerName == 'university' ? 'active' : ''}">--}%
            %{--<g:link controller="university">Universities</g:link>--}%
        %{--</li>--}%
        %{--<li id="configuration" class="${controllerName == 'configuration' ? 'active' : ''}">--}%
            %{--<g:link controller="configuration">Configuration</g:link>--}%
        %{--</li>--}%
    %{--</sec:ifAllGranted>--}%
</ul>
