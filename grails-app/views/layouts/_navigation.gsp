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
    <li id="projects">
        <a href="#">Projects</a>
    </li>
</ul>