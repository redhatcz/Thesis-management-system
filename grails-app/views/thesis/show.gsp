<%@ page import="com.redhat.theses.Topic" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'topic.label', default: 'Topic')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<div class="span8">
    <h1 class="header">
        <g:fieldValue bean="${thesisInstance.application.topic}" field="title"/>
    </h1>

    <g:if test="${thesisInstance?.thesisAbstract}">
        <markdown:renderHtml text="${topicInstance?.description}"/>
    </g:if>



    <richg:comments comments="${comments}" article="${topicInstance}" commentsTotal="${commentsTotal}"/>
</div>

<div class="span4">
    <div class="thesis">
        <h4>Thesis Information</h4>
        <dl class="dl-thesis">
            <dt><g:message code="thesis.status.label" default="Status"/></dt>
            <dd>
                <g:message code="thesis.status.${thesisInstance.status.toString().toLowerCase()}.label"
                           default="${thesisInstance.status.toString()}"/>
            </dd>
            <g:if test="${thesisInstance?.grade}">
                <dt><g:message code="thesis.grade.label" default="Grade"/></dt>
                <dd><g:fieldValue bean="${thesisInstance}" field="grade"/></dd>
            </g:if>

        </dl>
    </div>

    <div class="thesis">
        <h4>Thesis Management</h4>
    </div>
</div>

</body>
</html>
