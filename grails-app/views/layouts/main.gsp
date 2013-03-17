<g:applyLayout name="mainLayout">
    <head>
        <title><g:layoutTitle/></title>
        <g:layoutHead/>
    </head>
    <content tag="manage-box">
        <div class="container">
            <div class="pull-right">
                <a href="${createLink(controller: 'faq', action: 'list')}"><i class="icon-question-sign"></i> <g:message code="faq.label"/></a>
                <a href="#"><i class="icon-rss"></i> <g:message code="feed.news.label"/></a>
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
