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
    var detailsType = $this.attr('autocomplete-detailsType');
    detailsType = detailsType ? detailsType : 'nextTo';

    $(document).ready(function() {
        var map = undefined
        $this.typeahead({
            source: function(term, process) {
                var params = buildParams($this.attr('data-autocomplete-opts'));
                params['term'] = term;
                $.get($this.attr('data-autocomplete-url'), params, function(data) {
                    map = data;
                    process(data);
                });
            },
            updater: function(item, type) {
                var autocompleteTarget = $this.attr('data-autocomplete-target')
                if (type == "value" && autocompleteTarget) {
                    $('#' + escapeRegex(autocompleteTarget)).val(item);
                }
                return item;
            },
            deleter: function () {
                var autocompleteTarget = $this.attr('data-autocomplete-target')
                if (autocompleteTarget) {
                    $('#' + escapeRegex(autocompleteTarget)).val(null);
                }
            },
            detailsType: detailsType
        });
    });
}

