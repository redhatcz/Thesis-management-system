<%@ page import="com.redhat.theses.util.Util; com.redhat.theses.Topic" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta name="layout" content="main">
    <title><g:message code="topic.show.title" args="[topicInstance.title]"/></title>
</head>
<body>
    <div class="span8 content">
        <h1 class="header"><g:fieldValue bean="${topicInstance}" field="title"/></h1>
        <g:if test="${topicInstance?.secondaryTitle}">
            <h2 class="header" style="color: gray"><g:fieldValue bean="${topicInstance}" field="secondaryTitle"/></h2>
        </g:if>

        <h3 id="description">
            <i class="icon-pencil"></i>
            <g:message code="topic.description.label"/>
        </h3>
        <div class="tms-description">
            <markdown:renderHtml text="${topicInstance?.description}"/>
        </div>
        <g:if test="${topicInstance?.secondaryDescription}">
        <h3 id="description">
            <i class="icon-pencil"></i>
            <g:message code="topic.secondaryDescription.label"/>
        </h3>
        <div class="tms-description">
            <markdown:renderHtml text="${topicInstance?.secondaryDescription}"/>
        </div>
        </g:if>

        <g:set var="topicTags" value="${topicInstance?.tags}"/>
        <g:if test="${topicTags}">
        <p class="tag-list">
            <i class="icon-tags icon-large"></i>
            <g:message code="topic.tags.label" default="tags" />:
            <g:each in="${topicTags?.sort{it?.title}}" var="tag" status="i">
                <g:link action="list"
                        params="${[tagTitle: tag.title] + (params.categoryId ? [categoryId: params.categoryId] : [])}"
                >${tag?.title?.encodeAsHTML()}</g:link><g:if test="${topicTags?.size() - 1 != i}">,</g:if>
            </g:each>
        </p>
        </g:if>
        <g:else>
        <div class="tms-separator"></div>
        </g:else>

        <richg:comments comments="${comments}" article="${topicInstance}" commentsTotal="${commentsTotal}"/>
    </div>

    <div class="span4 sidebar">
        <div class="panel right">
            <h4><g:message code="topic.information.label"/></h4>
            <div class="panel-content">
                <dl>
                    <dt>
                        <i class="icon-user"></i>
                        ${message(code: 'topic.owner.label').toString().toLowerCase()}
                    </dt>
                    <dd>
                        <g:link controller="user" action="show" id="${topicInstance?.owner?.id}">${topicInstance?.owner?.encodeAsHTML()}</g:link>
                    </dd>

                    <dt>
                        <i class="icon-time"></i>
                        ${message(code: 'topic.dateCreated.label').toString().toLowerCase()}
                    </dt>
                    <dd>
                        <g:formatDate date="${topicInstance?.dateCreated}" dateStyle="LONG" type="date" />
                    </dd>

                <g:if test="${supervisions && !supervisions?.empty}">
                    <dt>
                        <i class="icon-group"></i>
                        ${message(code: 'topic.supervision.label').toString().toLowerCase()}
                    </dt>
                    <dd>
                        <g:each in="${supervisions}" status="i" var="supervision">
                        <g:link controller="user"
                                action="show"
                                id="${supervision?.supervisor?.id}"
                                title="${supervision?.university?.name}"
                            >${supervision?.supervisor?.encodeAsHTML()}</g:link>
                        <br />
                        </g:each>
                    </dd>
                </g:if>

                <g:if test="${topicInstance?.universities && !topicInstance?.universities?.empty}">
                    <dt>
                        <i class="icon-suitcase"></i>
                        ${message(code: 'topic.universities.label').toString().toLowerCase()}
                    </dt>
                    <dd>
                        <g:each in="${topicInstance?.universities?.sort{it?.name}}" status="i" var="university">
                        <g:link controller="university"
                                action="show"
                                id="${university?.id}"
                                title="${university?.name}"
                            ><g:fieldValue bean="${university}" field="name"/></g:link>
                        <br />
                        </g:each>
                    </dd>
                </g:if>

                <g:if test="${topicInstance?.types && !topicInstance?.types?.empty}">
                    <dt>
                        <i class="icon-book"></i>
                        ${message(code: 'topic.types.label').toString().toLowerCase()}
                    </dt>
                    <dd>
                    <g:each in="${topicInstance?.types?.sort{it.name()}}" status="i" var="type">
                        <g:message code="topic.type.${type?.toString()?.toLowerCase()}.label"/><g:if test="${i != topicInstance?.types?.size() - 1}"><span>, </span></g:if>
                    </g:each>
                    </dd>
                </g:if>

                <g:if test="${!topicInstance?.enabled}">
                    <dt>
                        <i class="icon-question-sign"></i>
                        ${message(code: 'topic.enabled.label').toString().toLowerCase()}
                    </dt>
                    <dd>
                        <g:formatBoolean boolean="${topicInstance.enabled}"/>
                    </dd>
                </g:if>
                </dl>
            </div>

            <h4><g:message code="topic.theses.label"/></h4>
            <div class="panel-content">
                <g:if test="${thesisList && !thesisList?.empty}">
                    <ul class="unstyled">
                    <g:each in="${thesisList?.sort{it.title}}" var="thesis">
                        <li>
                            <g:link controller="thesis" action="show" id="${thesis.id}"
                                    params="[headline: Util.hyphenize(thesis?.title)]">${thesis.title}</g:link>
                        </li>
                    </g:each>
                    </ul>
                </g:if>
                <g:else>
                    <p><g:message code="topic.no.theses.found"/></p>
                </g:else>
            </div>

            <h4><g:message code="topic.management.label"/></h4>
            <div class="panel-content">

                <g:form style="display: inline;">
                    <g:hiddenField name="topic.id" value="${topicInstance?.id}" />
                    <g:actionSubmit class="tms-btn tms-danger" action="delete"
                                    value="${message(code: 'default.delete.button')}"
                                    onclick="return confirm('${message(code: 'default.delete.confirm.message')}');" />
                </g:form>

                <g:if test="${!subscriber}">
                <g:form style="display: inline;" controller="subscription" action="subscribe">
                    <g:hiddenField name="articleId" value="${topicInstance?.id}"/>
                    <g:submitButton class="tms-btn tms-info" name="submit-subscription"
                                    value="${message(code: 'subscription.subscribe.button')}" />
                </g:form>
                </g:if>
                <g:else>
                <g:form style="display: inline;" controller="subscription" action="unsubscribe">
                    <g:hiddenField name="articleId" value="${topicInstance?.id}"/>
                    <g:submitButton class="tms-btn tms-info" name="submit-unsubscription"
                                    value="${message(code: 'subscription.unsubscribe.button')}"/>
                </g:form>
                </g:else>

                <g:if test="${topicInstance.enabled}">
                    <g:link class="tms-btn tms-success"
                            controller="application"
                            action="create"
                            id="${topicInstance?.id}"
                        ><g:message code="application.apply.button" /></g:link>
                </g:if>

                <g:link class="tms-btn tms-info"
                        controller="thesis"
                        action="create"
                        id="${topicInstance?.id}"
                    ><g:message code="thesis.create.button" /></g:link>

                <g:link class="tms-btn tms-warning"
                        action="edit"
                        id="${topicInstance?.id}"
                    ><g:message code="default.edit.button" /></g:link>

                <g:link class="tms-btn tms-warning"
                        controller="supervision"
                        action="manage"
                        id="${topicInstance?.id}"
                    ><g:message code="supervision.edit.mine.button" /></g:link>

            </div>
        </div>
    </div>
</body>
</html>
