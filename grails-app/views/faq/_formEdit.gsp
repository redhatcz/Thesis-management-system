<div class="control-group ${hasErrors(bean: faqInstance, field: 'locale', 'error')} required faq-locale">
    <label class="control-label" for="faq.locale">
        <strong><g:message code="faq.locale.label"/></strong>
    </label>

    <div class="controls">
        <g:select name="faq.locale"
                  from="${locales.entrySet()}"
                  value="${faqInstance?.locale?.toString() ?: 'en'}"
                  optionKey="key"
                  optionValue="value"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: faqInstance, field: 'question', 'error')} required faq-question">
    <label class="control-label" for="faq.question">
        <strong><g:message code="faq.question.label"/></strong>
    </label>

    <div class="controls">
        <g:textField name="faq.question" class="wide"
                     value="${faqInstance?.question}"
                     placeholder="${message(code: 'faq.question.label')}"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: faqInstance, field: 'answer', 'error')} required">
    <label class="control-label" for="faq.answer">
        <strong><g:message code="faq.answer.label"/></strong>
    </label>

    <div class="controls">
        <g:textArea name="faq.answer" rows="10" value="${faqInstance?.answer}"
                    placeholder="${message(code: 'faq.answer.label')}"/>
    </div>
</div>
