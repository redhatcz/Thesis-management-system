<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" %>
<g:applyLayout name="mainLayout">
    <head>
        <title><g:layoutTitle/></title>
        <g:layoutHead/>
    </head>
    <content tag="manage-box">
        <div class="container">
            <div class="pull-right">
                <g:if test="${RequestContextUtils.getLocale(request).toString().startsWith("cs")}">
                    <a id="lang" href="?lang=en"><i class="icon-globe"></i>
                        English</a>
                </g:if>
                <g:else>
                    <a id="lang" href="?lang=cs"><i class="icon-globe"></i>
                        Česky</a>
                </g:else>
                <a id="faq" href="${createLink(controller: 'faq', action: 'list')}"><i class="icon-question-sign"></i>
                    <g:message code="faq.label"/></a>
                <a id="news" href="${createLink(uri: '/rss')}"><i class="icon-rss"></i>
                    <g:message code="feed.news.label"/></a>
            </div>
        </div>
    </content>
    <content tag="container-box">
        <div class="span12">
            <g:render template="/layouts/navigation" />
            <g:render template="/layouts/messages" />
        </div>
        <g:layoutBody />
    </content>
</g:applyLayout>
