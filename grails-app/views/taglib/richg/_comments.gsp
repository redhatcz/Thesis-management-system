<%@ page import="com.redhat.theses.util.Util" %>

<h3 id="comments" class="header">Comments</h3>
<div class="comments">
    <g:if test="${comments}">
        <g:each in="${comments}" var="comment" status="i">
            <richg:comment comment="${comment}" index="${i}"/>
        </g:each>
    </g:if>
    <g:else>
        <p>${message(code: 'article.comments.nocomments', default: 'There are no comments for this article.')}</p>
    </g:else>
</div>

<g:if test="${Util.isPaginationVisible(commentsTotal, params.max)}">
    <g:paginate total="${commentsTotal}" params="[id: params.id]" class="pagination-centered"
                offset="${Util.lastOffset(commentsTotal, params.max, params.offset)}"/>
</g:if>

<div id="commentMessages"></div>

<h4 class="header">Leave a comment</h4>
<g:form controller="comment" action="create" name="create-comment-form">
    <g:hiddenField name="comment.article.id" value="${article?.id}" />
    <g:textArea class="comment" name="comment.content" rows="5" />
    <g:submitButton name="create-comment" class="tms-btn pull-right"
                    value="${message(code: 'default.button.post.label', default: 'Post Comment')}"/>
</g:form>

<script type="text/javascript">
    var form = $('#create-comment-form');
    sendForm(form, function(data) {
        $('.alert > .close').click();
        if (data.success) {
            window.location = '${createLink(action: 'show', params: [id: params.id])}';
        } else {
            $(data.message).appendTo('#commentMessages').hide().fadeIn();
        }
    });
</script>
