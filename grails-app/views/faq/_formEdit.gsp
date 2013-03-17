
    <div class="control-group ${hasErrors(bean: faqInstance, field: 'question', 'error')} required">
        <label class="control-label" for="faq.question">
            <strong><g:message code="faq.question.label"/></strong>
        </label>

        <div class="controls">
            <g:textField name="faq.question" value="${faqInstance?.question}"
                         placeholder="${message(code: 'faq.question.label')}"/>
        </div>
    </div>

    <div class="control-group ${hasErrors(bean: faqInstance, field: 'locale', 'error')} required">
        <label class="control-label" for="faq.question">
            <strong><g:message code="faq.locale.label"/></strong>
        </label>

        <div class="controls">
            <g:select name="faq.locale"
                      from="${locales.entrySet()}"
                      value="${faqInstance?.locale ?: 'en'}"
                      optionKey="key"
                      optionValue="value"/>
        </div>
    </div>

    <div class="control-group ${hasErrors(bean: faqInstance, field: 'answer', 'error')} required">
        <label class="control-label" for="faq.question">
            <strong><g:message code="faq.answer.label"/></strong>
        </label>

        <div class="controls">
            <g:textArea name="faq.answer" value="${faqInstance?.answer}"
                        placeholder="${message(code: 'faq.answer.label')}"/>
        </div>
    </div>








