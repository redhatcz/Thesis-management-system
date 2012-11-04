<div class="nav" role="navigation">
    <ul>
        <g:if test="${currentTag}">
            <g:if test="${currentTag.parent}">
                <li><g:link action="tag" id="${currentTag?.parent?.id}">${fieldValue(bean: currentTag?.parent, field: "title")}</g:link></li>
            </g:if>
            <g:else>
                <li><g:link action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            </g:else>
        </g:if>
        <g:each in="${tags}" var="tag">
            <li><g:link action="tag" id="${tag.id}">${fieldValue(bean: tag, field: "title")}</g:link></li>
        </g:each>
    </ul>
</div>