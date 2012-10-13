if (typeof jQuery !== 'undefined') {
	(function($) {
		$('#spinner').ajaxStart(function() {
			$(this).fadeIn();
		}).ajaxStop(function() {
			$(this).fadeOut();
		});
	})(jQuery);
}

function autocompletion(field_id, hidden_id, url) {
    $("#" + field_id).autocomplete({
        source: function(request, response){
            $.ajax({
                url: url,
                data: request,
                success: function(data){
                    response(data);
                }
            });
        },
        minLength: 2,
        select: function(event, ui) {
            $("#" + hidden_id).val(ui.item.id);
        }
    });
}
