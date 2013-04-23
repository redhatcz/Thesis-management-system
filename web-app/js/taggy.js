/**
 * @author vdedik@redhat.com
 */
(function($) {
    var Taggy = function(selectElem, taggyElem, options) {
        this.selectElem = selectElem;
        this.taggyElem = taggyElem;
        this.tagInput = taggyElem.find("input.taggy-tag-input");
        this.options = options;
        this.tagTpl = "<li id='taggy-choice-$placeholder' class='taggy-elem taggy-choice'><span class='taggy-choice-text'>$placeholder</span><a href='javascript:void(0)' class='taggy-choice-close'>&times;</a></li>";
        this.optionTpl = "<option value='$placeholder' selected>$placeholder</option>";

        //initialize tags
        $this = this;
        this.selectElem.find('option').each(function () {
            $this.addTag($(this).attr('value'));
        });

        //set max width of tagInput
        this.updateTagInputWidth()

        this.listen();
    }

    Taggy.prototype = {

        constructor: Taggy

        , addTag: function (label) {
            var filledTag = $(this.tagTpl.replace(/\$placeholder/g, label));

            filledTag.find('.taggy-choice-close').on('click', $.proxy(function() {this.removeTag(label)}, this));
            this.tagInput.parent().before(filledTag);

            this.updateTagInputWidth();
        }

        , removeTag: function (label) {
            this.taggyElem.find('#taggy-choice-' + label).remove();
            this.selectElem.find("option[value='" + label + "']").remove();

            this.updateTagInputWidth();
        }

        , click: function (e) {
            this.tagInput.focus();
        }

        , updateTagInputWidth: function () {
            var tagsWidth = 0
                , taggyElemWidth = this.taggyElem.width()
                , tagInputOuterSpaceWidth = this.tagInput.outerWidth(true) - this.tagInput.width();
            this.taggyElem.find('.taggy-choice').each(function () {
                tagsWidth += $(this).outerWidth(true);
                if (tagsWidth > taggyElemWidth) {
                    tagsWidth = $(this).outerWidth(true);
                }
            });

            var newTagInputWidth = taggyElemWidth - tagsWidth - tagInputOuterSpaceWidth;
            newTagInputWidth = newTagInputWidth < 30 ? taggyElemWidth : newTagInputWidth;
            this.tagInput.width(newTagInputWidth - 5);
        }

        , isTagPresent: function (label) {
            return this.taggyElem.find('#taggy-choice-' + label).length == 1;
        }

        , highlightTag: function (label) {
            var elem = this.taggyElem.find('#taggy-choice-' + label);
            elem.addClass('exists')
                .bind("transitionend webkitTransitionEnd oTransitionEnd MSTransitionEnd",
                function(){ elem.removeClass('exists'); });
        }

        , keydown: function (e) {
            // if backspace pressed and no value in text field, delete previous tag
            if (e.keyCode == 8 && this.tagInput.val() == '') {
                this.removeTag(this.taggyElem.find('.taggy-choice:last').find('.taggy-choice-text').text());
            }

            // if tab, space, comma or enter was not pressed, don't continue
            if (e.keyCode != 32 // spacebar
                && e.keyCode != 9 // tab
                && e.keyCode != 13 // enter
                && e.keyCode != 188 // comma
                ) {
                return;
            }

            // typeahead hack to select value on enter or tab
            if ((e.keyCode == 9 // tab
                || e.keyCode == 13) // enter
                && this.tagInput.data('typeahead').shown
                ) {
                this.tagInput.data('typeahead').select();
                // delete selected text so that no value is inserted on blur
                this.tagInput.data('typeahead').text = '';
            }

            // prevent default function of the key to take place
            e.preventDefault();

            // get label, trim it, lowercase it and remove all special chars except for dash
            var label = this.tagInput.val().trim().toLowerCase().replace(/[^\w-]/gi, '');

            // if label full of spaces, don't continue
            if (label == '') {
                return;
            }

            // if the tag isn't present, add it, if it is, highlight the one that is present
            if (!this.isTagPresent(label)) {
                this.addTag(label);
                this.tagInput.val('');

                var filledOption = this.optionTpl.replace(/\$placeholder/g, label);
                this.selectElem.append(filledOption);
            } else {
                this.highlightTag(label);
                this.tagInput.val('');
            }
        }

        , listen: function () {
            this.taggyElem.on('click', $.proxy(this.click, this));
            this.tagInput
                .on('keydown', $.proxy(this.keydown, this));
        }
    }

    $.fn.taggy = function (options) {
        options = $.extend({}, options);

        return this.each(function() {
            var $this = $(this)
                , tm = $("<ul class='taggy-multiselect'><li class='taggy-elem'><input class='taggy-tag-input' type='text' autocomplete='off'/></li></ul>");

            $this.css({display: 'none'});
            $this.after(tm);
            tm.data('taggy', new Taggy($this, tm, options));
        });
    }

})(jQuery);

$(document).ready(function(){
    $(".taggy-tag-input").unbind('keyup').keyup(function (e) {
        var $this = $(this).data('typeahead');

        switch(e.keyCode) {
            case 40: // down arrow
            case 38: // up arrow
            case 16: // shift
            case 17: // ctrl
            case 18: // alt
                break

            case 9: // tab
            case 13: // enter
            case 27: // escape
                if (!$this.shown) return
                $this.hide()
                break

            default:
                $this.lookup()
        }

        e.stopPropagation()
        e.preventDefault()
    });
});