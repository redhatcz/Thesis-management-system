<%@ page import="com.redhat.theses.AppStatus; com.redhat.theses.util.Util; com.redhat.theses.Category" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title><g:message code="request.show.title" args="[requestInstance.applicant.fullName]"/></title>
</head>

<body>
<div class="span8 content">
    <h2 class="header">
        <g:message code="request.show.title" args="[requestInstance.applicant.fullName]"/>
    </h2>

    <dl class="dl-horizontal tms-dl">
        <dt>
            <g:message code="application.applicant.label"/>
        </dt>
        <dd>
            <g:link controller="user" action="show" id="${requestInstance?.applicantId}">
                <g:fieldValue bean="${requestInstance?.applicant}" field="fullName"/>
            </g:link>
        </dd>
        <dt>
            <g:message code="application.status.label" />
        </dt>
        <dd>
            <g:message code="application.status.${requestInstance?.status?.toString()?.toLowerCase()}.label"/>
        </dd>
    </dl>
</div>

<g:if test="${canManage}"> 
    <div class="span4 sidebar">
        <div class="panel right">
            <h4><g:message code="application.management.label"/></h4>
            <div class="panel-content">
                <div class="panel-buttons">
                    <g:if test="${requestInstance?.status != AppStatus.APPROVED}">
                        <g:link class="tms-link btn-link" action="approve" id="${requestInstance?.id}">
                            <i class="icon-ok"></i>
                            <g:message code="application.approve.button"/>
                        </g:link>
                    </g:if>
                    <g:if test="${requestInstance?.status != AppStatus.DECLINED}">
                        <g:link class="tms-link btn-link" action="decline" id="${requestInstance?.id}"
                                onclick="return confirm('${message(code: 'default.decline.confirm.message')}');">
                            <i class="icon-ban-circle"></i>
                            <g:message code="application.decline.button"/>
                        </g:link>
                    </g:if>
                </div>
            </div>
        </div>
    </div>
</g:if>
</body>
</html>
