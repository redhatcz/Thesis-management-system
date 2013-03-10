<%@ page import="com.redhat.theses.util.Util" %>
<div class="span8 content">
<g:if test="${topicInstanceList.isEmpty()}">
    <p class="center muted">No topics were found.</p>
</g:if>

<g:else>
    <g:each var="topic" in="${topicInstanceList}">
        <div class="table-layout">
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
                <g:link class="gray-link" action="show" id="${topic.id}" params="[headline: Util.hyphenize(topic?.title)]"><g:message code="topic.text.more" default="Read more" />&hellip;</g:link>
            </div>
        </div>
    </g:each>
</g:else>

<g:if test="${Util.isPaginationVisible(topicInstanceTotal, params.max)}">
    <g:paginate total="${topicInstanceTotal}" class="pagination-centered" params="${params}"/>
</g:if>
</div>

<div class="span4 sidebar">
    <div class="panel right">
        <h4>Manage Topics</h4>
        <div class="panel-content">
            <g:if test="${currentCategory}">
                <g:link class="tms-btn tms-info" action="create" params="[categoryId: currentCategory?.id]">Create topic</g:link>
            </g:if>
            <g:else>
                <g:link class="tms-btn tms-info" action="create">Create topic</g:link>
            </g:else>
        </div>

        <g:if test="${currentCategory}">
            <h4>Category: ${currentCategory?.title}</h4>
        </g:if>
        <g:else>
            <h4>Categories</h4>
        </g:else>
        <div class="panel-content">
            <g:render template="categoryList" />
        </div>

        <h4>Tags</h4>
        <div class="panel-content">
            <g:render template="tagList" />
        </div>

    </div>
</div>
