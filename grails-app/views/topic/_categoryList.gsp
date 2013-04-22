<div role="navigation" class="nav-categories">
    <g:if test="${categoryList && categoryList.size() != 0}">
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
            <richg:link class="${category == currentCategory ? 'category-root' : 'category'} tms-tooltip" action="list"
                        params="${['filter.categories.id': category.id]}" removeParams="['max', 'offset']"
                        data-placement="left" title="${fieldValue(bean: category, field: "description")}"
            >${fieldValue(bean: category, field: "title")}</richg:link>
        </li>
        </g:each>
    </ul>
    </g:if>
    <g:else>
        <p class="muted"><g:message code="category.no.categories.found"/></p>
    </g:else>

    <div class="panel-buttons">
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <g:link class="tms-link btn-link" controller="category" action="create">
                <i class="icon-plus"></i>
                <g:message code="category.create.button"/>
            </g:link>
        </sec:ifAnyGranted>
    </div>
</div>
