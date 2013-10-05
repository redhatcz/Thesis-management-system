<%@ page import="com.redhat.theses.util.Util" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title><g:message code="university.show.title" args="[universityInstance.name]" /></title>
</head>
<body>
    <div class="span4 sidebar">
        <div class="panel left">
            <div class="avatar-university">
                <img src="${resource(dir: 'images', file: 'avatar-university.png')}">
            </div>
            <h4><g:message code="university.information.label"/></h4>
            <div class="panel-content">
                <dl>
                    <g:if test="${universityInstance?.name}">
                    <dt class="tms-tooltip" data-placement="left"
                        data-original-title="${message(code: 'university.name.label').toString()}">
                        <i class="icon-suitcase"></i>
                    </dt>
                    <dd>
                        <g:fieldValue bean="${universityInstance}" field="name"/>
                    </dd>
                    </g:if>
                    <g:if test="${universityInstance?.acronym}">
                        <dt class="tms-tooltip" data-placement="left"
                        data-original-title="${message(code: 'university.acronym.label').toString()}">
                            <i class="icon-asterisk"></i>
                        </dt>
                        <dd>
                            <g:fieldValue bean="${universityInstance}" field="acronym"/>
                        </dd>
                    </g:if>
                </dl>
            </div>
        </div>
    </div>
    <div class="span8 content">
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <div class="controls pull-right">
                <g:link class="tms-btn" action="edit"
                        id="${universityInstance?.id}"
                    ><i class="icon-wrench"></i><g:message code="default.edit.button"/></g:link>
            </div>
        </sec:ifAnyGranted>

        <h2><g:message code="university.show.topics.header"/></h2>
        <g:if test="${topicInstanceList && !topicInstanceList?.empty}">
            <ul class="tms-list unstyled">
            <g:each in="${topicInstanceList}" var="topic">
                <li class="tms-elem">
                    <i class="icon-book"></i>
                    <div class="tms-elem-link">
                        <g:link controller="topic" action="show"
                                id="${topic?.id}" params="${Util.hyphenize(topic?.title)}"
                        ><g:fieldValue field="title" bean="${topic}"/></g:link>
                        <span class="pull-right">
                            <i class="icon-comment"></i> ${commentCounts[topic] ?: 0}
                        </span>
                        <ul class="inline">
                            <li>
                                <i class="icon-time"></i>
                                <g:formatDate date="${topic?.dateCreated}"
                                              dateStyle="LONG"
                                              type="date" />
                            </li>
                        </ul>
                    </div>
                </li>
            </g:each>
            </ul>
            <div class="more">
                <i class="icon-reorder"></i>
                <g:link controller="topic" action="list"
                        params="['filter.universities.id': universityInstance?.id]">
                    <g:message code="more.button"/>
                </g:link>
            </div>
        </g:if>
        <g:else>
            <p class="center muted"><g:message code="university.no.topics.found" /></p>
        </g:else>
    </div>
</body>
</html>
