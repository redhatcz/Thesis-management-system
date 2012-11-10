<g:if test="${comments}">
    <h3 class="header">
        Comments
    </h3>
    <g:each in="${comments}" var="comment" status="i">
        <div class="comment" id="comment-${i}">
            <strong>${comment?.user.fullName}</strong>
            <markdown:renderHtml text="${comment?.content}" />
        </div>
    </g:each>
</g:if>

<h4 class="header">
    Leave a comment
</h4>
<g:textArea class="comment" name="comment.content" rows="5" />
<button class="btn btn-primary pull-right">
    <g:message code="default.button.post.label" default="Post Comment" />
</button>