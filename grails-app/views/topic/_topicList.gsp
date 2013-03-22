<%@ page import="com.redhat.theses.util.Util" %>
<div class="span8 content">
<g:if test="${topicInstanceList && !topicInstanceList.empty}">
    <g:each var="topic" in="${topicInstanceList}">
        <div class="table-layout ${!topic?.enabled ? 'disabled' : ''}">
            <h3>
                <g:link action="show" id="${topic.id}" params="[headline: Util.hyphenize(topic?.title)]">
                    <g:fieldValue bean="${topic}" field="title"/>
                </g:link>
            </h3>
            <ul class="inline">
                <li><i class="icon-user"></i>
                    <g:link controller="user" action="show" id="${topic?.ownerId}">${topic?.owner?.fullName}</g:link>
                </li>
                <li><i class="icon-time"></i>
                    <g:formatDate date="${topic?.dateCreated}"
                                  dateStyle="LONG"
                                  type="date" />
                </li>
                <li><i class="icon-comment"></i>
                    <g:link action="show" id="${topic.id}" fragment="comments">${commentCounts[topic] ?: 0} comments</g:link>
                </li>
            </ul>
            <div class="table-text">
                <g:fieldValue bean="${topic}" field="lead"/>
                <div class="read-more">
                    <g:link class="gray-link" action="show"
                            id="${topic.id}" params="[headline: Util.hyphenize(topic?.title)]"
                    ><g:message code="topic.read.more.label"/>&hellip;</g:link>
                </div>
            </div>
        </div>
    </g:each>
</g:if>

<g:else>
    <p class="center muted"><g:message code="topic.no.topics.found"/></p>
</g:else>

<g:if test="${Util.isPaginationVisible(topicInstanceTotal, params.max)}">
    <g:paginate total="${topicInstanceTotal}" class="pagination-centered"
                params="${Util.formatParams(request)}"/>
</g:if>
</div>

<div class="span4 sidebar">
    <div class="panel right">
        <g:if test="${currentCategory}">
            <h4><g:message code="category.label"/>: ${currentCategory?.title}</h4>
        </g:if>
        <g:else>
            <h4><g:message code="topic.categories.label"/></h4>
        </g:else>
        <div class="panel-content">
            <g:render template="categoryList" />
        </div>

        <h4><g:message code="topic.tags.label"/></h4>
        <div class="panel-content">
            <g:render template="tagList" />
        </div>
        <sec:ifAnyGranted roles="ROLE_OWNER">
            <h4><g:message code="topic.list.manage.label"/></h4>
            <div class="panel-content">
                <div class="panel-buttons">
                    <g:if test="${currentCategory}">
                        <g:link class="tms-link btn-link" action="create" params="[categoryId: currentCategory?.id]">
                            <i class="icon-plus"></i>
                            <g:message code="topic.create.button" />
                        </g:link>
                    </g:if>
                    <g:else>
                        <g:link class="tms-link btn-link" action="create">
                            <i class="icon-plus"></i>
                            <g:message code="topic.create.button"/>
                        </g:link>
                    </g:else>
                </div>
            </div>
        </sec:ifAnyGranted>
    </div>
</div>
