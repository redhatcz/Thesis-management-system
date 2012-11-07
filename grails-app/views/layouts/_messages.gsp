<g:if test="${flash.message}">
    <div class="alert alert-info" role="status">
        ${flash.message}
    </div>
</g:if>

<g:hasErrors>
    <div class="alert alert-error">
        <p><strong>Error!</strong></p>
        <g:eachError var="error">
            <p><g:message error="${error}"/>.</p>
        </g:eachError>
    </div>
</g:hasErrors>