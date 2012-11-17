<%@ page import="com.redhat.theses.util.Util" %>

<g:if test="${comments}">
    <h3 class="header">
        Comments
    </h3>
    <div id="comments">
        <g:each in="${comments}" var="comment" status="i">
            <richg:comment comment="${comment}" index="${i}"/>
        </g:each>
    </div>
</g:if>

<g:if test="${Util.isPaginationVisible(commentsTotal, params.max)}">
    <g:paginate total="${commentsTotal}" params="[id: params.id]"
                class="pagination-centered" offset="${Util.lastOffset(commentsTotal, params.max, params.offset)}"/>
</g:if>

<h4 class="header">
    Leave a comment
</h4>
<g:form controller="comment" action="create" name="create-comment-form">
    <g:hiddenField name="comment.article.id" value="${article?.id}" />
    <g:textArea class="comment" name="comment.content" rows="5" />
    <g:submitButton name="create-comment" class="btn btn-primary pull-right"
                    value="${message(code: 'default.button.post.label', default: 'Post Comment')}"/>
</g:form>
<script type="text/javascript">
    var form = $('#create-comment-form');
    form.submit(function() {
        $.ajax({
            url: form.attr('action'),
            type: form.attr('method'),
            data: form.serialize(),
            success: function(data) {
                $('.alert > .close').click();
                if (data.success) {
                    window.location = '${createLink(action: 'show', params: [id: params.id])}';
                } else {
                    $(data.message).appendTo('#comments').hide().fadeIn();
                }
            }
        });
        return false;
    });
</script>