<%@ page import="com.redhat.theses.util.Util" %>

<div class="table-layout ${!topic?.enabled ? 'disabled' : ''}">
    <div class="search-result">
        <div class="pull-left">
            <i class="icon-book icon-3x"></i>
        </div>
        <div class="result-content">
            <h6>
                <g:link controller="topic" action="show" id="${topic.id}"
                        params="[headline: Util.hyphenize(topic?.title)]">
                    <g:fieldValue bean="${topic}" field="title"/>
                </g:link>
            </h6>
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
        </div>
    </div>
</div>