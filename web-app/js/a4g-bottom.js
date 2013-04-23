
// save old typeahead
var _super = $.fn.typeahead;

// add new default options
$.extend(_super.defaults, {
    detailsType: 'nextTo',
    minWidth: 'auto'
});

// constructor
var Typeahead = function(element, options) {
    _super.Constructor.apply(this, arguments);
    this.deleter = this.options.deleter || this.deleter
    if (this.options.minWidth != 'auto') {
        this.$menu.css('min-width', this.options.minWidth);
    }
}

Typeahead.prototype = $.extend({}, _super.Constructor.prototype, {
    constructor: Typeahead,

    _super: function() {
        var args = $.makeArray(arguments);
        _super.Constructor.prototype[args.shift()].apply(this, args);
    },

    // override blur to select first option on blur
    blur: function (e) {
        this._super('blur', e)

        if (!this.$menu.is(':hover')){
            var li = this.$menu.find('.active').clone()
            li.find('.typeahead-details').remove()

            if ($.trim(li.text()) == this.text) {
                this.select()
            } else {
                this.deleter()
            }
        }
    },

    render: function (items) {
        var that = this
            , list = $([])

        $.map(items, function (item, value) {
            if (list.length >= that.options.items) return

            var li
                , a

            if ($.isArray(items)) value = item

            li = $(that.options.item)
            a = li.find('a').length ? li.find('a') : li
            a.html(that.highlighter(item))

            //this block is added
            if (that.options.detailsType == 'nextTo') {
                a.append(" <span class='typeahead-details'>(" + value + ")</span>")
            } else if (that.options.detailsType == 'newLine') {
                a.append("<br /><span class='typeahead-details'>" + value + "</span>")
            } else {
                // do not append anything
            }

            li.attr('data-value', value)
            if (li.find('a').length === 0) li.addClass('dropdown-header')

            list.push(li[0])
        })

        list.not('.dropdown-header').first().addClass('active')

        this.$menu.html(list)

        return this
    },

    select: function () {
        var li = this.$menu.find('.active')
            , val = li.attr('data-value')

        var html = li.find('.item-text').length > 0 ? li.find('.item-text') : li
        html.find('.typeahead-details').remove()
        var text = $.trim(html.text())

        val = this.updater(val, 'value')
        text = this.updater(text, 'text')

        this.$element
            .val(text)
            .attr('data-value', val)

        this.text = text

        if (typeof this.$target != 'undefined') {
            this.$target
                .val(val)
                .trigger('change')
        }

        this.$element.trigger('change')

        return this.hide()
    },

    deleter: function () {
        // do nothing, let the client implement
    }
});

// override the old initialization with the new constructor
$.fn.typeahead = $.extend(function(option) {

    var args = $.makeArray(arguments),
        option = args.shift();

    return this.each(function() {

        var $this = $(this);
        var data = $this.data('typeahead'),
            options = $.extend({}, _super.defaults, $this.data(), typeof option == 'object' && option);

        if ( !data ) {
            $this.data('typeahead', (data = new Typeahead(this, options)));
        }
        if (typeof option == 'string') {
            data[option].apply( data, args );
        }
        else if ( options.show ) {
            data.show.apply( data, args );
        }
    });

}, $.fn.typeahead);