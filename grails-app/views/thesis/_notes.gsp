<g:if test="${isThesisAdmin}">
    <a href="#notes" role="button" class="tms-link btn-link" data-toggle="modal">
        <i class="icon-file-alt"></i>
        <g:message code="thesis.notes.view.button"/>
        <span id="notes-changed" class="pull-right" style="display: none"
              data-toggle="tooltip" title="${message(code: 'thesis.notes.unsaved.tooltip')}">
            <i class="icon-warning-sign"></i>
        </span>
    </a>

    <div id="notes" class="modal hide fade">
        <g:formRemote name="thesis.notes"
                      url="[controller: 'thesis', action: 'updateNotes']">
            <div class="modal-wrapper">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h3><g:message code="thesis.notes.header"/></h3>
                </div>
                <div class="modal-body">
                    <g:hiddenField name="thesis.id" value="${thesisInstance?.id}"/>
                    <g:hiddenField name="notes-original" value="${thesisInstance?.notes}"/>

                    <label class="control-label" for="notes-text">
                        <strong><g:message code="thesis.notes.label"/></strong>
                    </label>
                    <div class="controls">
                        <g:textArea name="thesis.notes" value="${thesisInstance?.notes}"
                                    id="notes-text" rows="5"
                                    onkeyup="updateNotesDiff();"/>
                    </div>
                </div>
                <div class="modal-footer">
                    <g:submitToRemote class="tms-btn" name="save"
                                      value="${message(code: 'thesis.notes.update.button')}"
                                      url="[controller: 'thesis', action: 'updateNotes']"
                                      onSuccess="notesUpdated(data)"/>
                </div>
            </div>
        </g:formRemote>
    </div>

<g:set var="notesUrl"
       value="${createLink(controller: 'thesis', action: 'notes', id: thesisInstance?.id)}"/>
    <script type="text/javascript">
        checkNotesDiff = function() {
            var current = $('#notes-text').val();
            var original = $('#notes-original').val();

            return current == original;
        }

        updateNotesDiff = function() {
            if (checkNotesDiff()) {
                $('#notes-changed').hide()
            } else {
                $('#notes-changed').show()
            }
        }

        notesUpdated = function(data) {
            if (!data.success) {
                return false;
            }

            $('#notes-original').val(data.content);
            updateNotesDiff();
            $('#notes').modal('toggle');
        }


        $('#notes').on('hide', function() {
            updateNotesDiff();
        })

        $(window).on('beforeunload', function(){
            if (!checkNotesDiff()) {
                return '${message(code: 'thesis.notes.unsaved.message')}';
            }
        })
    </script>
</g:if>
