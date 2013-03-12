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
