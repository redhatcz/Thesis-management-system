<strong>${comment?.user?.fullName}</strong>

<div class="comment" id="comment-${index}">
    <div class="dropdown pull-right">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#"><i class="icon-cog large"></i></a>
        <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
            <%-- this button shows edit form bellow by javascript (see in richg.js) --%>
            <li>
                <a class="edit-comment" tabindex="-1" href="#">Edit</a>
            </li>
            <%-- this button triggers delete form bellow by javascript (see in richg.js) --%>
            <li>
                <a class="delete-comment" tabindex="-1" href="#">Delete</a>
            </li>
        </ul>
    </div>
    <g:form controller="comment"
            action="delete"
            style="display: none;"
            class="delete-comment-form"
            confirm-message="${message(code: 'default.delete.confirm.message')}">
        <g:hiddenField name="comment.id" value="${comment?.id}" id="commet-${index}-id-del"/>
    </g:form>
    <g:form controller="comment"
            action="update"
            style="display: none; padding: 5px;"
            class="edit-comment-form">
        <g:hiddenField name="comment.id"
                       value="${comment?.id}"
                       id="comment-${index}-id"/>
        <g:hiddenField name="comment.article.id"
                       value="${comment?.article?.id}"
                       id="comment-${index}-article" />
        <g:hiddenField name="comment.user.id"
                       value="${comment?.user?.id}"
                       id="comment-${index}-user"/>
        <g:hiddenField name="comment.dateCreated"
                       value="${comment?.dateCreated}"
                       id="comment-${index}-dateCreated"/>
        <g:textArea name="comment.content"
                    rows="5"
                    value="${comment?.content}"
                    id="comment-${index}-content" />
        <g:submitButton name="update-comment"
                        class="tms-btn pull-right"
                        id="comment-${index}-submit"
                        value="${message(code: 'default.update.button')}"/>
    </g:form>
    <div class="comment-content">
        <markdown:renderHtml text="${comment?.content}"/>
    </div>
</div>
