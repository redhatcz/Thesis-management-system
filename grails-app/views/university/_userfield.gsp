<div id="user-${i}" class="dynamic-list-child">
    <g:hiddenField name="users[${i}].id" value="${user?.id}"/>
    <a4g:autocomplete name="users[${i}].fullName" value="${user?.fullName}"
                            hiddenFieldId="users[${i}].id"
                            remoteUrl="${createLink(action: 'listUsersByName')}"/>
    <input type="button" class="delete" value="Delete" onclick="deleteUser('${i}')"/>
</div>