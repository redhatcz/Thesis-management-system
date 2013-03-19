<a id="notifications">
    <i class="icon-lightbulb large"></i><sub id="notifications-count">${notificationCount}</sub>
</a>
<script type="text/javascript">
    $(document).ready(function() {
        var popover = $('#notifications').popover({
            title: "${g.message(code: "notification.${notificationCount ? 'new' : 'no.new'}.title")}",
            content: '${popoverContent}',
            placement: 'bottom',
            html: true
        });

        $(document).on('click',function(e) {
            var $target = $(e.target);
            if (!$target.closest('.popover').length) {
                if (popover.hasClass('active')) {
                    popover.removeClass('active');

                    if ($('#notifications-count').text() != '0') {
                        $.get('${createLink(controller: 'notification', action: 'dismissAjax')}')
                        $('#notifications-count').text('0');
                    }
                } else {
                    popover.addClass('active');
                    if ($('#notifications-count').text() == '0') {
                        $('.popover .notification').addClass('old').parent().parent()
                                .find('.popover-title').text("${g.message(code: "notification.no.new.title")}");
                    }
                }
            }

            if (!$target.closest('.popover').length && !$target.closest('#notifications').length) {
                popover.popover('hide');
            }
        });
    });
</script>
