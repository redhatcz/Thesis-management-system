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
if ($("div.span8").height() < $("div.span4").height()) {

    $("div.span8").height(
        $("div.span4").height()
    );
}
