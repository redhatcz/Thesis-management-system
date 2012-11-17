<div class="comment" id="comment-${index}">
    <strong>${comment?.user?.fullName}</strong>
    <a class="edit-comment pull-right" href="#">Edit</a>
    <script type="text/javascript">
        $('#comment-${index} .edit-comment').click(function () {
            $('#comment-${index} .edit-comment-form').fadeIn();
            $('#comment-${index} .comment-content').hide();
            return false;
        });
    </script>

    <g:form controller="comment" action="update" style="display: none; padding: 5px;" class="edit-comment-form">
        <g:hiddenField name="comment.id" value="${comment?.id}"/>
        <g:hiddenField name="comment.article.id" value="${comment?.article?.id}" />
        <g:hiddenField name="comment.user.id" value="${comment?.user?.id}" />
        <g:hiddenField name="comment.dateCreated" value="${comment?.dateCreated}"/>
        <g:textArea class="comment" name="comment.content" rows="5" value="${comment?.content}" />
        <g:submitButton name="update-comment" class="btn btn-primary pull-right"
                        value="${message(code: 'default.button.update.label', default: 'Update')}"/>
    </g:form>
    <script type="text/javascript">
        $('#comment-${index} .edit-comment-form').submit(function () {
            var form = $(this);
            $.ajax({
                url: form.attr('action'),
                type: form.attr('method'),
                data: form.serialize(),
                success: function(data) {
                    $('.alert > .close').click();
                    if (data.success) {
                        location.reload();
                    } else {
                        $(data.message).insertBefore('#comment-${index}').hide().fadeIn();
                    }
                }
            });
            return false;
        });
    </script>

    <div class="comment-content">
        <markdown:renderHtml text="${comment?.content}"/>
    </div>
</div>