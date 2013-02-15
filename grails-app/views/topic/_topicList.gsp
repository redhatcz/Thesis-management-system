<%@ page import="com.redhat.theses.util.Util" %>

<div class="span8">
    <g:if test="${topicInstanceList.isEmpty()}">
        <p class="center muted">No topics were found.</p>
    </g:if>
    <g:else>
        <g:each var="topic" in="${topicInstanceList}">
            <div class="table-layout">
                <h3>
                    <g:link action="show" id="${topic.id}">
                        <g:fieldValue bean="${topic}" field="title"/>
                    </g:link>
                </h3>
                <ul class="inline">
                    <li><span class="tms-user"></span>
                        <g:link controler="user" action="show" id="${topic?.ownerId}">
                            ${topic?.owner?.fullName}
                        </g:link>
                    </li>
                    <li><span class="tms-clock"></span>
                        <g:formatDate date="${topic?.dateCreated}" dateStyle="LONG" type="date" />
                    </li>
                    <li><span class="tms-tag2"></span>
                        <g:set var="topicTags" value="${topic?.tags}"/>
                        <g:each in="${topicTags}" var="tag" status="i">
                            <g:link controller="tag" action="show" id="${tag?.id}">${tag?.title}</g:link><g:if test="${topicTags?.size() - 1 != i}">,</g:if>
                        </g:each>
                    </li>
                </ul>
                <div class="table-text">
                    <g:fieldValue bean="${topic}" field="lead"/>
                    <g:link class="gray-link" action="show" id="${topic.id}">
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
    <div class="thesis">
        <h4>Tags</h4>
        <g:render template="tagList" />
    </div>
    <div class="thesis">
        <h4>Manage Topics</h4>
        <g:link class="btn btn-info" action="create">
            <g:message code="default.new.label" args="[entityName]" />
        </g:link>
    </div>
</div>
