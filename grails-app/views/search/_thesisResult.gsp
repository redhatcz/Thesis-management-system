<%@ page import="com.redhat.theses.util.Util" %>

<div class="table-layout">
    <h4>
        <g:link controller="thesis" action="show" id="${thesis.id}"
                params="[headline: Util.hyphenize(thesis?.title)]">
            <g:fieldValue bean="${thesis}" field="title"/>
        </g:link>
    </h4>
    <ul class="inline">
        <li><i class="icon-user"></i>
            <g:link controller="user" action="show"
                    id="${thesis.assigneeId}">${thesis?.assignee?.fullName}</g:link>
        </li>
        <li><i class="icon-time"></i>
            <g:formatDate date="${thesis?.dateCreated}"
                          dateStyle="LONG"
                          type="date" />
        </li>
        <li><i class="icon-user"></i>
            <g:link controller="user" action="show" id="${thesis.supervisorId}">${thesis?.supervisor?.fullName}</g:link>
        </li>
    </ul>
</div>