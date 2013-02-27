if (typeof jQuery !== 'undefined') {
	(function($) {
		$('#spinner').ajaxStart(function() {
			$(this).fadeIn();
		}).ajaxStop(function() {
			$(this).fadeOut();
		});
	})(jQuery);
}

// Super simple script that sets the minimal height of the div.span8
// as high as the height of the div.span4
if ($("div.span8").height() < $("div.span4").height()) {
    $("div.span8").height($("div.span4").height());
}
