<%@ page import="com.redhat.theses.util.Util; com.redhat.theses.auth.User" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title><g:message code="faq.list.title"/></title>
</head>

<body>
<div class="span8 content">
    <h2 class="header"><g:message code="faq.list.header"/></h2>

    <div class="accordion faq-list" id="accordion2">
        <g:each in="${faqInstanceList}" var="faq">
            <div class="accordion-group">
                <div class="accordion-heading">
                    <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2"
                       href="#faq${faq.id}">
                        ${faq?.question?.encodeAsHTML()}
                    </a>
                    <g:link action="edit" id="${faq.id}">
                        <i class="icon-wrench"></i>
                    </g:link>
                    <g:link action="delete"
                            id="${faq.id}"
                            onclick="return confirm('${message(code: 'default.delete.confirm.message')}');">
                        <i class="icon-trash"></i>
                    </g:link>
                </div>

                <div id="faq${faq.id}" class="accordion-body collapse">
                    <div class="accordion-inner">
                        ${faq?.answer?.encodeAsHTML()}
                    </div>
                </div>
            </div>
        </g:each>
    </div>
    <g:javascript>
        $(document).ready(function () {
            var hash = window.location.hash.replace("#", "")

            if (hash) {
                $('body').find("#" + hash).addClass('in')
            }
        })
    </g:javascript>
</div>

<div class="span4 sidebar">
    <div class="panel right">
        <h4><g:message code="faq.list.manage.label"/></h4>

        <div class="panel-content">
            <g:link class="tms-btn tms-info" action="create"><g:message
                    code="faq.create.button"/></g:link>
        </div>
    </div>

    <sec:ifAllGranted roles="ROLE_ADMIN">
        <div class="panel right">
            <h4><g:message code="faq.locales.label"/></h4>

            <div class="panel-content">
                <div role="navigation">
                    <ul class="unstyled nav-categories">
                        <g:each in="${locales}" var="locale">
                            <g:set var="isCurrent" value="${locale.key == currentLocale}"/>
                            <g:link action="list"
                                    id="${locale.key}"
                                    class="${isCurrent ? 'category-root' : 'category'}" >${locale.value.encodeAsHTML()}</g:link>
                        </g:each>
                    </ul>
                </div>
            </div>
        </div>
    </sec:ifAllGranted>
</div>
</body>
</html>