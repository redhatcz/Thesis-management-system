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
        var elem = $("#" + UI.escapeRegex(elements[i]));
        var param = elem.attr('name');
        params[param] = elem.val();
    }
    return params
}