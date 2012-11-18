<div class="control-group ${hasErrors(bean: membershipCommand, field: 'memberships', 'error')}">
    <div class="controls">
        <g:hiddenField name="topic.id" value="${topicInstance?.id}" />
            <richg:multiCheckBox name="supervisions.memberships"
                                 from="${memberships}"
                                 value="${membershipCommand.memberships}"
                                 optionKey="id"
                                 label="organization"/>
    </div>
</div>