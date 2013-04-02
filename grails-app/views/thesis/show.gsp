<%@ page import="org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils; com.redhat.theses.util.Util; com.redhat.theses.Topic" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta name="layout" content="main">
    <title><g:message code="thesis.show.title" args="[thesisInstance.title]"/></title>
</head>
<body>
<div class="span8 content">
    <h1 class="header"><g:fieldValue bean="${thesisInstance}" field="title"/></h1>

    <g:if test="${thesisInstance?.thesisAbstract}">
    <h3 id="abstract">
        <i class="icon-quote-left"></i>
        <g:message code="thesis.thesisAbstract.label"/>
    </h3>
    <div class="tms-abstract">
        <p><g:fieldValue field="thesisAbstract" bean="${thesisInstance}"/></p>
    </div>
    </g:if>

    <h3 id="description">
        <i class="icon-pencil"></i>
        <g:message code="thesis.description.label"/>
    </h3>
    <div class="tms-description">
        <g:if test="${thesisInstance?.description}">
            <markdown:renderHtml text="${thesisInstance?.description}"/>
        </g:if>
    </div>

    <g:set var="thesisTags" value="${thesisInstance?.tags}"/>
    <g:if test="${thesisTags}">
        <div class="tag-list">
            <i class="icon-tags icon-large"></i>
            <g:message code="thesis.tags.label" default="tags" />:
            <g:each in="${thesisTags?.sort{it?.title}}" var="tag" status="i">
                <g:link action="list" class="tag"
                        params="${[tagTitle: tag.title] + (params.categoryId ? [categoryId: params.categoryId] : [])}"
                >${tag?.title?.encodeAsHTML()}</g:link><g:if test="${thesisTags?.size() - 1 != i}">,</g:if>
            </g:each>
        </div>
    </g:if>
    <g:else>
        <div class="tms-separator"></div>
    </g:else>

    <g:render template="fileUpload" />


    <richg:comments comments="${comments}" article="${thesisInstance}" commentsTotal="${commentsTotal}"/>
</div>
<div class="span4 sidebar">
    <div class="panel right">
        <h4><g:message code="thesis.information.label"/></h4>
        <div class="panel-content">
            <dl>
                <dt>
                    <i class="icon-book"></i>
                    ${message(code: 'topic.label').toString().toLowerCase()}
                </dt>
                <dd>
                    <g:link action="show" controller="topic" id="${thesisInstance?.topicId}"
                            params="[headline: Util.hyphenize(thesisInstance?.topic?.title)]"><g:fieldValue field="topic" bean="${thesisInstance}"/></g:link>
                </dd>
                <dt>
                    <i class="icon-user"></i>
                    ${message(code: 'thesis.assignee.label').toString().toLowerCase()}
                </dt>
                <dd>
                    <g:link controller="user" action="show" id="${thesisInstance?.assigneeId}"><g:fieldValue field="assignee" bean="${thesisInstance}"/></g:link>
                </dd>
                <g:if test="${thesisInstance?.supervisor}">
                <dt>
                    <i class="icon-user mini"></i>
                    ${message(code: 'thesis.supervisor.label').toString().toLowerCase()}
                </dt>
                <dd>
                    <g:link controller="user" action="show" id="${thesisInstance?.supervisorId}"><g:fieldValue field="fullName" bean="${thesisInstance?.supervisor}"/></g:link>
                </dd>
                </g:if>
                <dt>
                    <i class="icon-time"></i>
                    ${message(code: 'thesis.dateCreated.label').toString().toLowerCase()}
                </dt>
                <dd>
                    <g:formatDate date="${thesisInstance?.dateCreated}" dateStyle="LONG" type="date" />
                </dd>
                <dt>
                    <i class="icon-question-sign"></i>
                    ${message(code: 'thesis.status.label').toString().toLowerCase()}
                </dt>
                <dd>
                <g:if test="${thesisInstance?.status?.toString() != 'FINISHED'}">
                    <g:message code="thesis.status.${thesisInstance?.status?.toString()?.toLowerCase()}.label" />
                </g:if>
                <g:else>
                    <g:message code="thesis.status.awarded.grade" args="[thesisInstance?.grade]"/>
                </g:else>
                </dd>
            </dl>
        </div>

        <sec:ifLoggedIn>
        <h4><g:message code="thesis.manage.label"/></h4>
        <div class="panel-content">
            <div class="panel-buttons">
                <g:render template="notes"/>
                <g:if test="${!subscriber}">
                    <g:form controller="subscription" action="subscribe">
                        <g:hiddenField name="articleId" value="${thesisInstance?.id}"/>
                        <button class="tms-link btn-link" name="submit-subscription">
                            <i class="icon-rss"></i>
                            <g:message code="subscription.subscribe.button" />
                        </button>
                    </g:form>
                </g:if>

                <g:else>
                    <g:form controller="subscription" action="unsubscribe">
                        <g:hiddenField name="articleId" value="${thesisInstance?.id}"/>
                        <button class="tms-link btn-link" name="submit-unsubscription">
                            <i class="icon-rss"></i>
                            <g:message code="subscription.unsubscribe.button" />
                        </button>
                    </g:form>
                </g:else>

                <sec:ifLoggedIn>
                    <g:set var="currentUserId" value="${sec.loggedInUserInfo(field: 'id')?.toLong()}"/>
                    <g:if test="${thesisInstance?.supervisorId == currentUserId ||
                            thesisInstance?.topic?.ownerId == currentUserId ||
                            thesisInstance?.assigneeId == currentUserId ||
                            SpringSecurityUtils.ifAnyGranted('ROLE_ADMIN')}">
                        <g:link class="tms-link btn-link" controller="thesis"
                                action="edit" id="${thesisInstance?.id}">
                            <i class="icon-wrench"></i>
                            <g:message code="default.edit.button"/>
                        </g:link>
                    </g:if>
                    <g:if test="${thesisInstance?.supervisorId == currentUserId ||
                            thesisInstance?.topic?.ownerId == currentUserId ||
                            SpringSecurityUtils.ifAnyGranted('ROLE_ADMIN')}">
                        <g:form action="delete">
                            <g:hiddenField name="thesis.id" value="${thesisInstance?.id}" />
                            <button type="submit" class="tms-link btn-link"
                                    onclick="return confirm('${message(code: 'default.delete.confirm.message')}');">
                                <i class="icon-trash"></i>
                                <g:message code="default.delete.button" />
                            </button>
                        </g:form>
                    </g:if>
                </sec:ifLoggedIn>

            </div>
        </div>
        </sec:ifLoggedIn>
    </div>
</div>
</body>
</html>
