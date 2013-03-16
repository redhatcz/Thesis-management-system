<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title><g:message code="university.show.title" args="[universityInstance.name]" /></title>
</head>
<body>
    <div class="span4 sidebar">
        <div class="panel left">
            <div class="avatar">
                %{--University logo will be here soon!--}%
            </div>
            <h4><g:message code="university.information.label"/></h4>
            <div class="panel-content">
                <dl>
                    <g:if test="${universityInstance?.name}">
                    <dt>
                        <i class="icon-suitcase"></i>
                        ${message(code: 'university.name.label').toString().toLowerCase()}
                    </dt>
                    <dd>
                        <g:fieldValue bean="${universityInstance}" field="name"/>
                    </dd>
                    </g:if>
                </dl>
            </div>
        </div>
    </div>
    <div class="span8 content">
        <div class="controls pull-right">
            <g:link class="tms-btn" action="edit"
                    id="${universityInstance?.id}"
                ><i class="icon-wrench"></i><g:message code="default.edit.button"/></g:link>
        </div>
    </div>
</body>
</html>
