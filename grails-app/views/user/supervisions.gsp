<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="user/showLayout"/>
    <title><g:message code="user.supervisions.title"/></title>
</head>
<body>
<content tag="active-button">theses</content>

<content tag="main-box">
    <g:if test="${topicInstanceList && !topicInstanceList?.empty}">
        <g:each var="topic" in="${topicInstanceList}">
            <div class="table-layout">
                <h4>
                    <g:link controller="supervision"
                            action="manage" id="${topic.id}"
                    ><g:fieldValue bean="${topic}" field="title"/></g:link>
                </h4>
                <g:fieldValue bean="${topic}" field="lead"/>
            </div>
        </g:each>
    </g:if>

    <g:else>
        <p class="center muted"><g:message code="topic.no.topics.found"/></p>
    </g:else>
</content>
</body>
</html>
