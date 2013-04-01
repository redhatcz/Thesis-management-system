<%@ page import="com.redhat.theses.util.Util" %>

<div class="table-layout ${!topic?.enabled ? 'disabled' : ''}">
    <h3>
        <g:link controller="topic" action="show" id="${topic.id}"
                params="[headline: Util.hyphenize(topic?.title)]">
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