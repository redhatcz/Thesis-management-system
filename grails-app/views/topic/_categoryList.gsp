<div role="navigation" class="nav-categories">
    <ul class="unstyled">
        <g:each in="${categoryList}" var="category">
        <li>
            <sec:ifAnyGranted roles="ROLE_ADMIN">
                <g:form controller="category" action="delete">
                    <g:hiddenField name="category.id" value="${category?.id}"/>
                    <button type="submit" class="category-settings btn-link"
                            onclick="return confirm('${message(code: 'default.delete.confirm.message')}');">
                        <i class="icon-trash"></i>
                    </button>
                </g:form>
                <g:link class="category-settings" controller="category" action="edit"
                        id="${category?.id}"><i class="icon-wrench"></i></g:link>
            </sec:ifAnyGranted>
            <g:link class="${category == currentCategory ? 'category-root' : 'category'}" action="list"
                    params="${[categoryId: category.id] + (params.tagTitle ? [tagTitle: params.tagTitle] : [])}"
                >${fieldValue(bean: category, field: "title")}</g:link>
        </li>
        </g:each>
    </ul>
    <div class="panel-buttons">
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <g:link class="tms-link btn-link" controller="category" action="create">
                <i class="icon-plus"></i>
                <g:message code="category.create.button"/>
            </g:link>
        </sec:ifAnyGranted>
    </div>
</div>
