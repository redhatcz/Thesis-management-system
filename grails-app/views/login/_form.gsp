<div class="control-group">
    <label class="control-label" for='username'>
        <g:message code="security.login.email.label" default="Email"/>
    </label>
    <div class="controls">
        <input class="input-block-level wide"
               type='text' name='j_username'
               id='email' placeholder="${message(code: 'security.login.email.label')}" />
    </div>
</div>

<div class="control-group">
    <label class="control-label" for='password'>
        <g:message code="security.login.password.label"/>
    </label>
    <div class="controls">
        <input class="input-block-level wide"
               type='password' name='j_password'
               id='password' placeholder="${message(code: 'security.login.password.label')}" />
    </div>
</div>

<div class="control-group">
    <div class="controls">
        <input type='submit' id="submit" class="tms-btn pull-right" value='${message(code: "security.login.button")}'/>
        <label class="checkbox inline" for='remember_me'>
            <input type='checkbox' name='${rememberMeParameter}' id='remember_me' <g:if test='${hasCookie}'>checked='checked'</g:if>/>
            <g:message code="security.login.remember.me.label"/>
        </label>
    </div>
</div>
