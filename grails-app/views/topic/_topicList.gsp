<%@ page import="com.redhat.theses.util.Util" %>

<div class="span8">
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
                    <li><span class="entypo-user"></span>
                        <g:link controller="user" action="show" id="${topic?.ownerId}">
                            ${topic?.owner?.fullName}
                        </g:link>
                    </li>
                    <li><span class="entypo-clock"></span>
                        <g:formatDate date="${topic?.dateCreated}" dateStyle="LONG" type="date" />
                    </li>
                    <li><span class="entypo-comment"></span>
                        <g:link action="show" id="${topic.id}" fragment="comments">${commentCounts[topic] ?: 0} comments</g:link>
                    </li>
                </ul>
                <div class="table-text">
                    <g:fieldValue bean="${topic}" field="lead"/>
                    <g:link class="gray-link" action="show" id="${topic.id}" params="[headline: Util.hyphenize(topic?.title)]">
                        <g:message code="topic.text.more" default="Read more" />&hellip;<!--Three dots-->
                    </g:link>
                </div>
            </div>
        </g:each>
    </g:else>

    <g:if test="${Util.isPaginationVisible(topicInstanceTotal, params.max)}">
        <g:paginate total="${topicInstanceTotal}" class="pagination-centered"/>
    </g:if>
</div>

<div class="span4">
    <div class="panel right">
        <h4>Manage Topics</h4>
        <div class="panel-content">
            <g:link class="tms-btn tms-info" action="create">
                <g:message code="default.new.label" args="[entityName]" />
            </g:link>
        </div>
        <h4>Tags</h4>
        <div class="panel-content">
            <g:render template="tagList" />
        </div>
    </div>
</div>
