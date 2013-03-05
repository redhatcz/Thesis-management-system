<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title><g:message code="default.show.configuration" default="Configuration" /></title>
</head>
<body>
<div class="span8">
    <h1 class="header"><g:message code="default.show.configuration" default="Configuration" /></h1>
    <dl class="dl-horizontal">
        <dt>Allowed Email domains</dt>
        <g:if test="${config?.emailDomains}">
            <g:each in="${[config?.emailDomains].flatten()}" var="emailDomain">
            <dd>${emailDomain}</dd>
            </g:each>
        </g:if>
        <g:else>
            <dd><em>empty</em></dd>
        </g:else>
    </dl>
</div>
<div class="span4">
    <div class="panel right">
        <h4>Configuration management</h4>
        <div class="panel-content">
            <g:link class="tms-btn tms-warning" action="edit"><g:message code="configuration.edit.button" default="Edit" /></g:link>
        </div>
    </div>
</div>
</body>
</html>
