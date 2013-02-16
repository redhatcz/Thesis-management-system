<div role="navigation">
    <g:if test="${currentTag}">
        <g:if test="${currentTag.parent}">
            <g:link class="tag tag-root" action="tag" id="${currentTag?.parent?.id}">
                ${fieldValue(bean: currentTag?.parent, field: "title")}
            </g:link>
        </g:if>
        <g:else>
            <g:link class="tag tag-root" action="list">
                <g:message code="default.list.label" args="[entityName]" />
            </g:link>
        </g:else>
    </g:if>
    <g:each in="${tags}" var="tag">
        <g:link class="tag" action="tag" id="${tag.id}">
            ${fieldValue(bean: tag, field: "title")}
        </g:link>
    </g:each>
</div>
