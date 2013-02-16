<%@ page import="com.redhat.theses.Tag" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'tag.label', default: 'Tag')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<div class="span8">
    <h1 class="header">
        <g:fieldValue bean="${applicationInstance.applicant}" field="fullName"/>  -
        <g:fieldValue bean="${applicationInstance.topic}" field="title"/>
    </h1>

    <dl class="dl-horizontal">
        <dt><g:message code="application.applicant.label" default="Applicant"/></dt>
        <dd><g:fieldValue bean="${applicationInstance.applicant}" field="fullName"/></dd>

        <dt><g:message code="topic.label" default="Topic"/></dt>
        <dd><g:fieldValue bean="${applicationInstance.topic}" field="title"/></dd>

        <dt><g:message code="organisation.university.label" default="University"/></dt>
        <dd><g:fieldValue bean="${applicationInstance.university}" field="name"/></dd>

        <dt><g:message code="application.suprvisor.label" default="Preferred supervisor"/></dt>
        <dd><g:fieldValue bean="${applicationInstance.supervisor}" field="fullName"/></dd>


        <dt><g:message code="application.date.label" default="Application date"/></dt>
        <dd><g:fieldValue bean="${applicationInstance}" field="dateCreated"/></dd>
    </dl>
</div>

<div class="span4">
    <div class="thesis">
        <h4>Thesis Management</h4>
        <g:form>
            <fieldset class="buttons">
                <g:hiddenField name="tag.id" value="${applicationInstance?.id}" />
                <g:link class="tms-btn tms-warning" action="approve" id="${applicationInstance?.id}">
                    <g:message code="application.button.approve.label" default="Approve" />
                </g:link>
             </fieldset>
        </g:form>
    </div>
</div>
</body>
</html>
