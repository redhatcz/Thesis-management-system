<richg:dynamicField id="faq-list"
                    class="dynamic-field-faq"
                    childClass="dynamic-field-child-faq"
                    for="${faqCommand?.faq}"
                    var="faq" index="i" initSize="0">

    <g:hasErrors bean="${faq}">
        <div class="alert alert-error">
            <button type="button" class="close" data-dismiss="alert">×</button>
            <p><strong>Error!</strong></p>

            <g:eachError bean="${faq}" var="error">
                <p><g:message error="${error}"/>.</p>
            </g:eachError>
        </div>
    </g:hasErrors>

    <div class="control-group ${hasErrors(bean: faq, field: 'locale', 'error')} required faq-locale">
        <label class="control-label" for="faqCommand.faq[${i}].locale">
            <strong><g:message code="faq.locale.label"/></strong>
        </label>
        <div class="controls">
            <g:select name="faqCommand.faq[${i}].locale"
                      from="${locales.entrySet()}"
                      value="${faq?.locale?.toString() ?: 'en'}"
                      optionKey="key"
                      optionValue="value"/>
        </div>
    </div>

    <div class="control-group ${hasErrors(bean: faq, field: 'question', 'error')} required faq-question">
        <label class="control-label" for="faqCommand.faq[${i}].question">
            <strong><g:message code="faq.question.label"/></strong>
        </label>
        <div class="controls">
            <g:textField name="faqCommand.faq[${i}].question"
                         value="${faq?.question}" class="wide"
                         placeholder="${message(code: 'faq.question.label')}"/>
        </div>
    </div>

    <div class="control-group ${hasErrors(bean: faq, field: 'answer', 'error')} required">
        <label class="control-label" for="faqCommand.faq[${i}].answer">
            <strong><g:message code="faq.answer.label"/></strong>
        </label>
        <div class="controls">
            <g:textArea name="faqCommand.faq[${i}].answer"
                        value="${faq?.answer}" rows="10"
                        placeholder="${message(code: 'faq.answer.label')}"/>
        </div>
    </div>

</richg:dynamicField>







