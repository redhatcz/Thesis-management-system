<g:applyLayout name="mainLayout">
    <head>
        <title><g:layoutTitle/></title>
        <g:layoutHead/>
    </head>

    <content tag="manage-box">
        <div class="container">
            <div class="pull-right">
                <a href="#"><span class="entypo-rss"></span> News Feed</a>
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
