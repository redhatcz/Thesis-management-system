<div role="navigation">
    <ul class="unstyled">
    <g:if test="${currentTag}">
        <g:if test="${currentTag.parent}">
        <li><g:link class="tag-root" action="tag" id="${currentTag?.parent?.id}">Parent</g:link></li>
        </g:if>
        <g:else>
        <li><g:link class="tag-root" action="list">Root</g:link></li>
        </g:else>
    </g:if>
    <g:each in="${tags}" var="tag">
    <li><g:link class="tag" action="tag" id="${tag.id}">
        ${fieldValue(bean: tag, field: "title")}</g:link></li>
    </g:each>
    </ul>
</div>
