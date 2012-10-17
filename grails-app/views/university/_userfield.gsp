<div id="user-${i}" class="dynamic-list-child">
    <a4g:autocomplete remoteUrl="${createLink(action: 'listUsersByName')}">
        <g:hiddenField name="users[${i}].id" value="${user?.id}"/>
        <g:textField name="users[${i}].fullName" value="${user?.fullName}"/>
    </a4g:autocomplete>
    <input type="button" class="delete" value="Delete" onclick="deleteUser('${i}')"/>
</div>