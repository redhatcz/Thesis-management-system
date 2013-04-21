/**
 * RichGSP javascript file
 *
 * Dependencies: JQuery
 */

function addDynamicField(variable, size) {
    var cloneId = "#dynamic-field-" + variable + "-clone";
    var buttonId = "#add-" + variable + "-button";
    //clone last element
    var clone = $(cloneId).clone();

    //replace last element's 'clone' with listCount
    var cloneHtml = clone[0].outerHTML.replace(/clone/g, size);

    //prepend the clone before button
    $(buttonId).before(cloneHtml);

    //make it visible and enabled
    var currentDynamicFieldId = "#dynamic-field-" + variable + "-" + size;
    $(currentDynamicFieldId).removeAttr("style");
    $(currentDynamicFieldId + " :input").removeAttr("disabled");

    //change add field
    var newSize = size + 1;
    $(buttonId + " > input").attr("onclick", "addDynamicField('" + variable + "'," + newSize + ")");
}

function sendForm(form, func) {
    form.submit(function() {
        $.ajax({
            url: form.attr('action'),
            type: form.attr('method'),
            data: form.serialize(),
            success: function(data) {
                func(data);
            }
        });
        return false;
    });
}

jQuery.fn.multiselect = function() {
    $(this).each(function() {
        var checkboxes = $(this).find("input:checkbox");
        checkboxes.each(function() {
            var checkbox = $(this);
            // Highlight pre-selected checkboxes
            if (checkbox.attr("checked"))
                checkbox.parent().addClass("multiselect-on");

            // Highlight checkboxes that the user selects
            checkbox.click(function() {
                if (checkbox.attr("checked"))
                    checkbox.parent().addClass("multiselect-on");
                else
                    checkbox.parent().removeClass("multiselect-on");
            });
        });
    });
};

$(document).ready(function() {

    /**
     * edit button for all comments
     */
    $('.comment').each(function () {
        var $this = $(this);
        $this.find('.edit-comment').click(function() {
            $this.find('.edit-comment-form').fadeIn();
            $this.find('.comment-content').hide();
            $this.find('.open').removeClass('open');
            return false;
        });
    });

    /**
     * update form for all comments
     */
    $('.comment').each(function() {
        var $this = $(this);
        sendForm($this.find('.edit-comment-form'), function(data) {
            $('.alert > .close').click();
            if (data.success) {
                location.reload();
            } else {
                $(data.message).insertBefore($this).hide().fadeIn();
            }
        });
    });

    /**
     * delete button for all comments
     */
    $('.comment').each(function() {
        var $this = $(this);
        var form = $this.find('.delete-comment-form');

        sendForm(form, function(data) {
            if (data.success) {
                location.reload();
            } else {
                $(data.message).insertBefore(form).hide().fadeIn();
            }
        });

        $this.find('.delete-comment').click(function() {
            var message = form.attr('confirm-message');
            var result = window.confirm(message);
            if (result) {
                form.submit();
            }
            return result;
        });
    });

    $(".multiselect").multiselect();
});

