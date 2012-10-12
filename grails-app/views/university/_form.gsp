<%@ page import="com.redhat.theses.University" %>



<div class="fieldcontain ${hasErrors(bean: universityInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="university.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${universityInstance?.name}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: membershipInstance, field: 'user', 'error')} ">
    <label for="users">
        <g:message code="membership.user.label" default="Users" />

    </label>
    <div class="dynamic-list">
        <div class="dynamic-list-child">
            <g:hiddenField name="user.id"/>
            <g:textField name="user"/>
        </div>
        <div>
            <g:submitButton name="add-user" value="Add"/>
        </div>
    </div>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#user").autocomplete({
                source: function(request, response){
                    $.ajax({
                        url: "${createLink(action: 'listUsersByName')}",
                        data: request,
                        success: function(data){
                            response(data);
                        }
                    });
                },
                minLength: 2,
                select: function(event, ui) {
                    $("#user\\.id").val(ui.item.id);
                }
            });
        });
    </script>
</div>
