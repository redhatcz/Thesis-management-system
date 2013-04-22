if (typeof jQuery !== 'undefined') {
	(function($) {
		$('#spinner').ajaxStart(function() {
			$(this).fadeIn();
		}).ajaxStop(function() {
			$(this).fadeOut();
		});
	})(jQuery);
}

// Super simple script that sets the minimal height of the class "span8"
// as high as the height of the class "span4"
if ($(".span8.content").height() < $(".span4.sidebar").height()) {

    $(".span8.content").height(
        $(".span4.sidebar").height()
    );
}

addToFileList = function (tableId, infoId) {
    var inner = function (event, id, name, data) {
        if (data.success == false) {
            return false
        }

        var table = $("#" + tableId);
        var info =  $("#" + infoId);

        table.show();
        info.hide();

        var clone = table.find('tr.clone-me');
        var newRow = clone.clone();
        newRow.removeClass('clone-me');
        newRow.attr('data-file', data.id);

        //TODO: this is somewhat clumsy and should be improved
        newRow.html(decodeURIComponent(newRow.html()))
        newRow.html(newRow.html().replace(/\{filename\}/g, data.filename));
        newRow.html(newRow.html().replace(/\{bucket\}/g, data.bucket));
        newRow.html(newRow.html().replace(/\{id\}/g, data.id));
        newRow.html(newRow.html().replace(/\{type\}/g, data.type));
        newRow.html(newRow.html().replace(/\{date\}/g, data.date));

        newRow.insertBefore(clone)
        newRow.show()
    }
    return inner
}

removeFromFileList = function (tableId, infoId, data) {
    if (data.success == false) {
        return false
    }

    var table = $("#" + tableId);
    var info =  $("#" + infoId);

    var row = table.find("tr[data-file='" + data.id + "']")
    row.remove()

    var rowCount = table.find('tbody tr').length - 1

    if (rowCount == 0) {
        table.hide()
        info.show()
    }
}

$(document).ready(function() {

    // disable all empty fields on submit for filter
    $('form.filter').submit(function() {
        $(this).find(':input[value=""]').attr('disabled', true);
        $(this).find(':input[type="submit"]').attr('disabled', true);

        return true;
    });

    // tooltip initialization
    $('.tms-tooltip').tooltip();
});