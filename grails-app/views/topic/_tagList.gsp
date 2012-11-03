<div class="nav" role="navigation">
    <g:if test="${tags}">
        <ul>
            <g:each in="${tags}" var="tag">
                <li><g:link action="tag" id="${tag.id}">${fieldValue(bean: tag, field: "title")}</g:link></li>
            </g:each>
        </ul>
    </g:if>
</div>