<a href="#list-topic" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>

<g:render template="tagList" />

<div id="list-topic" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>
            <g:sortableColumn property="title" title="${message(code: 'topic.title.label', default: 'Title')}" />

            <th><g:message code="topic.owner.label" default="Owner" /></th>

            <g:sortableColumn property="dateCreated" title="${message(code: 'topic.dateCreated.label', default: 'Date Created')}" />
        </tr>
        </thead>
        <tbody>
        <g:each in="${topicInstanceList}" status="i" var="topicInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                <td><g:link action="show" id="${topicInstance.id}">${fieldValue(bean: topicInstance, field: "title")}</g:link></td>

                <td>${fieldValue(bean: topicInstance, field: "owner")}</td>

                <td><g:formatDate date="${topicInstance.dateCreated}" /></td>
            </tr>
        </g:each>
        </tbody>
    </table>
    <div class="pagination">
        <g:paginate total="${topicInstanceTotal}" />
    </div>
</div>