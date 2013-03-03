<div class="control-group ${hasErrors(bean: universityCommand, field: 'university', 'error')}">
    <div class="controls">
        <g:hiddenField name="topic.id" value="${topicInstance?.id}" />
        <richg:multiCheckBox name="universityCommand.universities"
                             from="${universities}"
                             value="${universityCommand.universities}"
                             optionKey="id"
                             label="name"/>
    </div>
</div>