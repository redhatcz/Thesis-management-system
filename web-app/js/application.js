if (typeof jQuery !== 'undefined') {
	(function($) {
		$('#spinner').ajaxStart(function() {
			$(this).fadeIn();
		}).ajaxStop(function() {
			$(this).fadeOut();
		});
	})(jQuery);
}

function autocompletion(field_id, hidden_id, url, optElements) {
    $("#" + escapeSelector(field_id)).autocomplete({
        source: function(request, response){
            var params = buildParams(optElements.split(" "));
            console.log(params);

            params['term'] = request.term;
            $.ajax({
                url: url,
                data: params,
                success: function(data){
                    response(data);
                }
            });
        },
        minLength: 2,
        select: function(event, ui) {
            $("#" + escapeSelector(hidden_id)).val(ui.item.id);
        }
    });
}

function escapeSelector(id) {
    return id.replace(/([\[\]\.])/g, "\\$1");
}

function buildParams(elements){
    var params = {};
    for (var i=0; i < elements.length; ++i){
        var elem = $("#" + escapeSelector(elements[i]));
        var param = elem.attr('name');
        params[param] = elem.val();
    }
    return params
}


