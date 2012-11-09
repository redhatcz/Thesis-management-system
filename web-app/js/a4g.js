/**
 * A4G javascript functions
 *
 * Dependencies: JQuery, bootstrap-typeahead
 */

function escapeRegex(text) {
    return text.replace(/[-[\]{}()*+?.,\\^$|#\s]/g, "\\$&");
}

function buildParams(elements){
    var params = {};
    if (elements != undefined) {
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

function autocomplete(id) {
    var $this = $("#" + escapeRegex(id));
    $this.attr("autocomplete", "off");
    $(document).ready(function() {
        $this.typeahead({
            source: function(term, process) {
                var params = buildParams($this.attr('autocomplete-opts').split(" "));
                params['term'] = term;
                console.log(params);
                $.get($this.attr('autocomplete-url'), params, function(data) {
                    map = data;
                    labels = $.map(data, function(value, key) {
                        return key;
                    });
                    process(labels);
                });
            },
            updater: function(item) {
                $('#' + escapeRegex($this.attr('autocomplete-target'))).val(map[item]);
                return item;
            }
        });
        $this.blur(function() {
            var term = $this.val();
            $.get($this.attr('autocomplete-url'), {term: term}, function(data) {
                var exactMatch = false;
                $.each(data, function(key, value) {
                    if (key == term) {
                        exactMatch = true;
                    }
                });
                if (exactMatch) {
                    map = data;
                    $this.data('typeahead').updater(term);
                } else {
                    $('#' + escapeRegex($this.attr('autocomplete-target'))).val(null);
                }

            });
        });
    });
}