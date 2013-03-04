<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="user/showLayout"/>
    <title>Show user: theses</title>
</head>
<body>
<content tag="active-button">theses</content>

<content tag="main-box">
    <table class="table">
        <thead>
        <tr>
            <g:sortableColumn property="id" title="${message(code: 'thesis.id.label', default: 'Id')}"/>
            <g:sortableColumn property="topic" title="${message(code: 'thesis.topic.label', default: 'Thesis topic')}"/>
            <g:sortableColumn property="status" title="${message(code: 'thesis.status.label', default: 'Status')}"/>
        </tr>
        </thead>
        <tbody>
        <g:each in="${thesisInstanceList}" status="i" var="thesisInstance">
            <tr>
                <td>
                    <g:link action="show" id="${thesisInstance.id}">
                        <g:fieldValue field="id" bean="${thesisInstance}"/>
                    </g:link>
                </td>
                <td>
                    <g:fieldValue field="title" bean="${thesisInstance?.topic}"/>
                </td>
                <td>
                    <g:message code="thesis.status.${thesisInstance?.status?.toString()?.toLowerCase()}.label" />
                </td>
            </tr>
        </g:each>
        </tbody>
    </table>

    <!-- TODO: link to more theses -->
    <a href="#">More ...</a>
</content>
</body>
</html>
