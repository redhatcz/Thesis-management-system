<g:if test="${flash.message}">
<div class="alert alert-info" role="status">
    <button type="button" class="close" data-dismiss="alert">×</button>
    <i class="icon-info-sign"></i>
    ${flash.message}
</div>
</g:if>

<g:hasErrors>
<div class="alert alert-error">
    <button type="button" class="close" data-dismiss="alert">×</button>

    <i class="icon-remove-sign"></i>
    <p><strong>Error!</strong></p>

    <g:eachError var="error">
    <p><g:message error="${error}"/>.</p>
    </g:eachError>
</div>
</g:hasErrors>
