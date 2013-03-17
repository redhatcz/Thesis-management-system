<%@ page import="org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils" %>
<div class="comment-head">
    <img class="img-circle" src="${resource(dir: 'images', file: 'avatar-mini.png')}"/>
    <h4>${comment?.user?.fullName}</h4>
    <div class="pull-right">
        <i class="icon-time"></i>
        <g:formatDate date="${comment?.dateCreated}" dateStyle="LONG" type="date" />
    </div>
</div>

<div class="comment" id="comment-${index}">
    <sec:ifLoggedIn>
        <g:if test="${SpringSecurityUtils.ifAnyGranted('ROLE_ADMIN, ROLE_OWNER') ||
                sec.loggedInUserInfo(field: 'id').toLong() == comment?.userId}">
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
            <g:textArea name="comment.content"
                        rows="5"
                        value="${comment?.content}"
                        id="comment-${index}-content" />
            <g:submitButton name="update-comment"
                            class="tms-btn pull-right"
                            id="comment-${index}-submit"
                            value="${message(code: 'default.update.button')}"/>
        </g:form>

        </g:if>
    </sec:ifLoggedIn>
    <div class="comment-content">
        <markdown:renderHtml text="${comment?.content}"/>
    </div>
</div>
