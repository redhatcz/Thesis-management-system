<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="user/showLayout"/>
    <title>Show user: theses</title>
</head>
<body>
<content tag="active-button">theses</content>

<content tag="main-box">
    <g:if test="${thesisInstanceList && thesisInstanceList.size() != 0}">
        <table class="table">
            <thead>
            <tr>
                <g:sortableColumn property="title" title="${message(code: 'thesis.title.label', default: 'Thesis title')}"/>
            </tr>
            </thead>
            <tbody>
            <g:each in="${thesisInstanceList}" status="i" var="thesisInstance">
                <tr>
                    <td>
                        <g:link action="show" id="${thesisInstance.id}"><g:fieldValue field="title" bean="${thesisInstance}"/></g:link>
                    </td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </g:if>
    <g:else>
        <p>There are no theses.</p>
    </g:else>
    <g:if test="${thesisInstanceList?.size() > 5}">
        <!-- TODO: link to more theses -->
        <a href="#">More ...</a>
    </g:if>
</content>
</body>
</html>
