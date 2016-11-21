<%@ page import="com.redhat.theses.auth.Role; com.redhat.theses.util.Util; com.redhat.theses.auth.User" %>
<g:applyLayout name="main">
<html>
<head>
    <title><g:layoutTitle /></title>
    <g:layoutHead/>
</head>
<body>
	<div class="span8 content">
	    <ul class="nav nav-tabs">
	        <li class="${pageProperty(name: 'page.active-button') == 'list' ? 'active' : ''}">
	            <g:link action="list" controller="user">
	                <i class="icon-list"></i> <g:message code="user.list.label"/>
	            </g:link>
	        </li>
	        <li class="${pageProperty(name: 'page.active-button') == 'roleRequest' ? 'active' : ''}">
	            <g:link action="list" controller="roleRequest">
	                <i class="icon-asterisk"></i> <g:message code="request.list.label"/>
	            </g:link>
	        </li>
	    </ul>
	    <div>
	        <g:pageProperty name="page.main-box"/>
	    </div>
	</div>
	<div class="span4 sidebar">
	    <g:pageProperty name="page.sidebar"/>
	</div>
</body>
</html>
</g:applyLayout>
