<%@ page import="com.redhat.theses.University" %>



<div class="fieldcontain ${hasErrors(bean: universityInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="university.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${universityInstance?.name}"/>
</div>

