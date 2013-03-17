<a href="#" id="notifications">
    <i class="icon-lightbulb large"></i><sub id="notifications-count">${notificationCount}</sub>
</a>
<script type="text/javascript">
    $(document).ready(function() {
        var popover = $('#notifications').popover({
            title: "${popoverTitle}",
            content: '${popoverContent}',
            placement: 'bottom',
            html: true
        }).click(function(e) {
            var $this = $(this);
            if ($this.hasClass('active')) {
                $this.removeClass('active');

                if ($('#notifications-count').text() != '0') {
                    $.get('${createLink(controller: 'notification', action: 'dismissNotifications')}')
                    $('#notifications-count').text('0');
                }
            } else {
                $(this).addClass('active');
                if ($('#notifications-count').text() == '0') {
                    $('.notification.new').removeClass('new');
                }
            }
            e.preventDefault();
        });
    });
</script>
