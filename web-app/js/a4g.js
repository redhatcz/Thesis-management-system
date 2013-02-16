/**
 * A4G javascript functions
 *
 * Dependencies: JQuery, bootstrap-typeahead
 */

function escapeRegex(text) {
    return text.replace(/[-[\]{}()*+?.,\\^$|#\s]/g, "\\$&");
}

function buildParams(opts){
    var params = {};
    if (opts != undefined) {
        var elements = opts.split(" ");
        for (var i=0; i < elements.length; ++i){
            var idAndAlias = elements[i].split("@");
            var elem = $("#" + escapeRegex(idAndAlias[0]));

            if (!idAndAlias[1]) {
                var param = elem.attr('name');
                params[param] = elem.val();
            } else {
                params[idAndAlias[1]] = elem.val();
            }
        }
    }
    return params;
}

function dynamicSelect(id) {
    var $this = $("#" + escapeRegex(id));
    var noSelection = $this.find(':first-child')
    $(document).ready(function () {
        var sourceSelector = '#' + escapeRegex($this.attr('source')).replace(/\\\s/g, ",#");
        var source = $(sourceSelector);
        source.change(function () {
            var params = buildParams($this.attr('remote-opts'));
            $.get($this.attr('remote-url'), params, function (data) {
                $this.html(noSelection);
                map = data;
                $.each(data, function (key, value) {
                    $this.html($this.html() + '<option value="' + key + '">' + value + '</option>')
                })

            });
        })
    })
}

function autocomplete(id) {
    var $this = $("#" + escapeRegex(id));
    $this.attr("autocomplete", "off");
    $(document).ready(function() {
        $this.typeahead({
            source: function(term, process) {
                var params = buildParams($this.attr('autocomplete-opts'));
                params['term'] = term;
                $.get($this.attr('autocomplete-url'), params, function(data) {
                    process(data);
                });
            },
            updater: function(item, type) {
                //clear the input if the autocomplete dropdown is hidden
                var result = this.shown ? item : undefined;
                if (type == "value") {
                    $('#' + escapeRegex($this.attr('autocomplete-target'))).val(result);
                }
                return result;
            }
        });
    });
}

