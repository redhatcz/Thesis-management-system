<%@ page import="org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils" %>
<div class="comment-head">
    <richg:avatar user="${comment?.user}" small="true"
                  class="img-circle" height="36" width="36"/>
    <h4>${comment?.user?.fullName}</h4>
    <div class="pull-right">
        <g:if test="${comment?.privateComment}">
            <span class="comment-private-label"><g:message code="comment.privateComment.label"/></span>,
        </g:if>
        <i class="icon-time"></i>
        <g:formatDate date="${comment?.dateCreated}" dateStyle="LONG" type="date" />
    </div>
</div>

<div class="comment ${comment?.privateComment ? 'private' : 'public'}" id="comment-${index}">
    <sec:ifLoggedIn>
        <g:if test="${SpringSecurityUtils.ifAnyGranted('ROLE_ADMIN, ROLE_OWNER') ||
                sec.loggedInUserInfo(field: 'id').toLong() == comment?.userId}">
        <div class="dropdown pull-right">
            <a class="dropdown-toggle" data-toggle="dropdown"><i class="icon-cog large"></i></a>
            <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
                <%-- this button shows edit form bellow by javascript (see in richg.js) --%>
                <li>
                    <a class="edit-comment" tabindex="-1">Edit</a>
                </li>
                <%-- this button triggers delete form bellow by javascript (see in richg.js) --%>
                <li>
                    <a class="delete-comment" tabindex="-1">Delete</a>
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
        <g:form controller="comment" action="update" class="edit-comment-form" style="display: none;">
            <g:hiddenField name="comment.id" value="${comment?.id}" id="comment-${index}-id"/>
            <g:textArea name="comment.content" rows="5" value="${comment?.content}" id="comment-${index}-content" />
            <button type="submit" name="update-comment" class="tms-btn pull-right" id="comment-${index}-submit">
                <i class="icon-refresh"></i>
                <g:message code="default.update.button"/>
            </button>
            <sec:ifAnyGranted roles="ROLE_ADMIN, ROLE_OWNER, ROLE_SUPERVISOR">
                <label for="comment-${index}-private" class="pull-left">
                    <g:checkBox name="comment.privateComment"
                                value="${comment?.privateComment}"
                                id="comment-${index}-private"/>
                    <g:message code="comment.privateComment.label"/>
                </label>
            </sec:ifAnyGranted>
        </g:form>

        </g:if>
    </sec:ifLoggedIn>
    <div class="comment-content">
        <markdown:renderHtml text="${comment?.content}"/>
    </div>
</div>
