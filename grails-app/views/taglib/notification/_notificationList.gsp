<g:if test="${notifications && !notifications?.empty}">
    <g:each in="${notifications}" var="notification">
        <g:render template="/taglib/notification/notification"
                  model="[isNew: !notification?.seen, message: message(code: notification?.feed?.messageCode, args: notification?.feed?.args)]"/>
    </g:each>
    <g:if test="${showMoreButton}">
        <div class="more">
            <i class="icon-reorder"></i>
            <g:link action="list" controller="notification"><g:message code="more.button"/></g:link>
        </div>
    </g:if>
</g:if>
<g:else>
    <div><g:message code="notification.no.notifications"/></div>
</g:else>
