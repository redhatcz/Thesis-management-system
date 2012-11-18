<div class="comment" id="comment-${index}">
    <strong>${comment?.user?.fullName}</strong>
    <g:form controller="comment" action="delete" class="form-inline pull-right delete-comment-form"
            confirm-message="${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}">
        <g:hiddenField name="comment.id" value="${comment?.id}" id="commet-${index}-id-del"/>
        <g:submitButton name="delete-comment" class="btn-link delete-comment-submit" id="comment-${index}-delete"
                        value="${message(code: 'default.button.delete.label', default: 'Delete')}"/>
    </g:form>
    <span class="pull-right">&nbsp;|&nbsp;</span>
    <a class="edit-comment pull-right" href="#">Edit</a>

    <g:form controller="comment" action="update" style="display: none; padding: 5px;" class="edit-comment-form">
        <g:hiddenField name="comment.id" value="${comment?.id}" id="comment-${index}-id"/>
        <g:hiddenField name="comment.article.id" value="${comment?.article?.id}" id="comment-${index}-article" />
        <g:hiddenField name="comment.user.id" value="${comment?.user?.id}" id="comment-${index}-user"/>
        <g:hiddenField name="comment.dateCreated" value="${comment?.dateCreated}" id="comment-${index}-dateCreated"/>
        <g:textArea class="comment" name="comment.content" rows="5" value="${comment?.content}" id="comment-${index}-content" />
        <g:submitButton name="update-comment" class="btn btn-primary pull-right" id="comment-${index}-submit"
                        value="${message(code: 'default.button.update.label', default: 'Update')}"/>
    </g:form>
    <div class="comment-content">
        <markdown:renderHtml text="${comment?.content}"/>
    </div>
</div>