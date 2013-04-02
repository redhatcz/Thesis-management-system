<%@ page import="com.redhat.theses.util.Util" %>
<h3 id="comments" class="header">
    <i class="icon-comments"></i>
    <g:message code="comment.title"/>
</h3>
<div class="comments">
    <g:if test="${comments}">
    <g:each in="${comments}" var="comment" status="i">
        <richg:comment comment="${comment}" index="${i}"/>
    </g:each>
    </g:if>

    <g:else>
    <p class="well">${message(code: 'comment.no.comments')}</p>
    </g:else>
</div>

<g:if test="${Util.isPaginationVisible(commentsTotal, params.max)}">
    <g:paginate total="${commentsTotal}"
                params="${params}"
                class="pagination-centered"
                offset="${Util.lastOffset(commentsTotal, params.max, params.offset)}"/>
</g:if>

<div id="commentMessages"></div>
<sec:ifLoggedIn>
<g:form controller="comment" action="create" name="create-comment-form">
    <g:hiddenField name="comment.article.id" value="${article?.id}" />
    <sec:ifAnyGranted roles="ROLE_ADMIN, ROLE_OWNER, ROLE_SUPERVISOR">
        <label id="private-checkbox" for="comment-new-private" class="pull-right">
            <g:checkBox name="comment.privateComment"
                        id="comment-new-private"/>
            <g:message code="comment.privateComment.label"/>
        </label>
    </sec:ifAnyGranted>
    <h4 class="header"><g:message code="comment.post.title"/></h4>
    <g:textArea name="comment.content" rows="5" />
    <div class="small-msg pull-left">
        <i class="icon-info-sign"></i>
        <g:message code="field.markdown.label" />
    </div>
    <button type="submit" name="create-comment" class="tms-btn pull-right">
        <i class="icon-comment"></i>
        <g:message code="comment.post.button"/>
    </button>
</g:form>
<script type="text/javascript">
    var form = $('#create-comment-form');
    sendForm(form, function(data) {
        $('.alert > .close').click();
        if (data.success) {
            window.location = '${createLink(action: 'show', params: [id: params.id, headline: params.headline])}';
        } else {
            $(data.message).appendTo('#commentMessages').hide().fadeIn();
        }
    });
</script>
</sec:ifLoggedIn>
