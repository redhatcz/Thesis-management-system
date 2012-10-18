/**
 * A4G javascript functions
 *
 * Dependencies: JQuery, JQuery-UI
 */

UI = $.ui.autocomplete;

function autocompletion(inputFieldId, outputFieldId, url, optElements) {
    $("#" + UI.escapeRegex(inputFieldId)).autocomplete({
        source: function(request, response){
            var params = buildParams(optElements.split(" "));

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
            if (outputFieldId) {
                $("#" + UI.escapeRegex(outputFieldId)).val(ui.item.id);
            }
        }
    });
}

function buildParams(elements){
    var params = {};
    for (var i=0; i < elements.length; ++i){
        var idAndAlias = elements[i].split("@");
        var elem = $("#" + UI.escapeRegex(idAndAlias[0]));

        if (!idAndAlias[1]) {
            var param = elem.attr('name');
            params[param] = elem.val();
        } else {
            params[idAndAlias[1]] = elem.val();
        }
    }
    return params
}