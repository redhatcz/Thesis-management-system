<%@ page import="com.redhat.theses.University" %>



<div class="fieldcontain ${hasErrors(bean: universityInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="university.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${universityInstance?.name}"/>
</div>
<div class="fieldcontain">
    <label for="users">
        <g:message code="university.users.label" default="Users" />

    </label>
    <div id="add-users-list" class="dynamic-list">
        <g:each in="${usersCommand?.users}" var="user" status="i">
            <g:render template="userfield" model="['user': user, 'i': i]"/>
        </g:each>
        <g:render template="userfield" model="['i': 'clone']"/>

        <div id="add-user">
            <input type="button" value="Add User" onclick="addUser()"/>
        </div>

        <script type="text/javascript">
            var usersCount = ${usersCommand?.users?.size()};

            function addUser() {
                //clone last element
                var clone = $("#user-clone").clone();

                //replace last element's 'clone' with membershipsCount
                var userCloneHtml = $("#user-clone")[0].outerHTML.replace(/clone/g, usersCount);
                $("#user-clone").replaceWith(userCloneHtml);

                //prepend the clone before button 'add-user'
                $("#add-user").before(clone);

                //increment membershipsCount
                usersCount++;
            }

            function deleteUser(id) {
                if (id != 'clone') {
                    $('#user-' + id).remove();
                }
            }
        </script>
    </div>
</div>
