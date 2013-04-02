<fieldset>
    <legend><g:message code="config.termsOfUse.header" /></legend>
    <div class="control-group">
        <label class="control-label" for="configuration.termsOfUse_en">
            <strong><g:message code="locale.en.label" /></strong>
        </label>
        <div class="controls">
            <g:textArea name="configuration.termsOfUse_en" rows="5"
                        value="${config?.termsOfUse_en}"/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label" for="configuration.termsOfUse_cs">
            <strong><g:message code="locale.cs.label" /></strong>
        </label>
        <div class="controls">
            <g:textArea name="configuration.termsOfUse_cs" rows="5"
                        value="${config?.termsOfUse_cs}"/>
        </div>
    </div>
</fieldset>

<fieldset>
    <legend><g:message code="config.announcement.header" /></legend>
    <div class="control-group">
        <div class="small-msg pull-right">
            <i class="icon-info-sign"></i>
            <g:message code="field.markdown.label" />
        </div>
        <label class="control-label" for="configuration.announcement">
            <strong><g:message code="config.announcement.label" /></strong>
        </label>
        <div class="controls">
            <g:textArea name="configuration.announcement" rows="5"
                        value="${config?.announcement}"/>
        </div>
    </div>
</fieldset>

<fieldset>
    <legend><g:message code="config.email.domains.header" /></legend>
    <div class="control-group">
        <label class="control-label" for="email-domain-list">
            <strong><g:message code="config.email.domains.label" /></strong>
        </label>
        <div class="controls">
            <g:set var="emailDomains" value="${config?.emailDomains ? [config?.emailDomains].flatten() : []}"/>
            <richg:dynamicField id="email-domain-list"
                                for="${emailDomains}"
                                var="emailDomain"
                                index="i">
                <g:textField name="configuration.emailDomains"
                             id="configuration.emailDomains[${i}]"
                             value="${emailDomain}"/>
            </richg:dynamicField>
        </div>
    </div>
</fieldset>