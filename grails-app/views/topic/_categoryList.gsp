<div role="navigation">
    <ul class="unstyled nav-categories">
        <g:if test="${currentCategory}">
        <sec:ifAllGranted roles="ROLE_ADMIN">
        <li>
            <g:link class="category-root"
                    controller="category"
                    action="edit"
                    params="${currentCategory?.id}"><g:message code="category.edit.button"/></g:link>
        </li>
        <li>
            <g:link class="category-root"
                    controller="category"
                    action="delete"
                    id="${currentCategory?.id}"
                    onclick="return confirm('${message(code: 'default.delete.confirm.message')}');"><g:message code="category.delete.button"/></g:link>
        </li>
        </sec:ifAllGranted>
        </g:if>

        <g:each in="${categoryList}" var="category">
        <li>
            <g:link class="${category == currentCategory ? 'category-root' : 'category'}" action="list"
                    params="${[categoryId: category.id] + (params.tagTitle ? [tagTitle: params.tagTitle] : [])}"
            >${fieldValue(bean: category, field: "title")}</g:link>
        </li>
        </g:each>

        <sec:ifAllGranted roles="ROLE_ADMIN">
        <li>
            <g:link class="category-root"
                    controller="category"
                    action="create"><g:message message="category.new.button"/></g:link>
        </li>
        </sec:ifAllGranted>
    </ul>
</div>
