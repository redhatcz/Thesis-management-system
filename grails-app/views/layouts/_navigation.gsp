<div class="span12">
    <ul class="nav nav-pills">
        <li id="news-feed" class="${pageProperty(name:'body.nav-button') == 'news-feed' ? 'active' : ''}">
            <a href="/theses-management">News Feed</a>
        </li>
        <li id="topics" class="${pageProperty(name:'body.nav-button') == 'topics' ? 'active' : ''}">
            <g:link controller="topic">Topics</g:link>
        </li>
        <li id="theses">
            <a href="#">Theses</a>
        </li>
        <li id="projects">
            <a href="#">Projects</a>
        </li>
    </ul>

    <g:if test="${flash.message}">
        <div class="alert alert-info" role="status">
            ${flash.message}
        </div>
    </g:if>
</div>