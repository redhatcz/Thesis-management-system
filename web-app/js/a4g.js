/**
 * A4G javascript functions
 *
 * Dependencies: JQuery, JQuery-UI
 */

function autocompletion(inputFieldId, outputFieldId, url, optElements) {
    $("#" + escapeSelector(inputFieldId)).autocomplete({
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
                $("#" + escapeSelector(outputFieldId)).val(ui.item.id);
            }
        }
    });
}

function escapeSelector(id) {
    return id.replace(/([\[\]\.])/g, "\\$1");
}

function buildParams(elements){
    var params = {};
    for (var i=0; i < elements.length; ++i){
        var elem = $("#" + escapeSelector(elements[i]));
        var param = elem.attr('name');
        params[param] = elem.val();
    }
    return params
}