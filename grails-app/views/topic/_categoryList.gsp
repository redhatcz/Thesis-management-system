<div role="navigation">
    <ul class="unstyled">
    <g:if test="${currentCategory}">
        <li>
            <g:link class="category-root" action="list">Back</g:link>
        </li>
        <sec:ifAllGranted roles="ROLE_ADMIN">
        <li>
            <g:link class="category-root" controller="category" action="edit" id="${currentCategory?.id}">Edit category</g:link>
        </li>
        <li>
            <g:link class="category-root" controller="category" action="delete" id="${currentCategory?.id}"
                    onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"
            >Delete category</g:link>
        </li>
        </sec:ifAllGranted>
    </g:if>
    <g:else>
        <g:each in="${categoryList}" var="category">
        <li>
            <g:link class="category" action="category" id="${category.id}">${fieldValue(bean: category, field: "title")}</g:link>
        </li>
        </g:each>
        <sec:ifAllGranted roles="ROLE_ADMIN">
        <li>
            <g:link class="category-root" controller="category" action="create">Create new category</g:link>
        </li>
        </sec:ifAllGranted>
    </g:else>
    </ul>
</div>
