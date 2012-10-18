/**
 * A4G javascript functions
 *
 * Dependencies: JQuery, JQuery-UI
 */

UI = $.ui.autocomplete;

/**
 * This definition ensures that the written value in the autocomplete text field is selected even though the user
 * wrote the value instead of clicking on it from the autocomplte box.
 */
(function( $ ) {
    $.ui.autocomplete.prototype.options.autoSelect = true;
    $( ".ui-autocomplete-input" ).live( "blur", function( event ) {
        var autocomplete = $( this ).data( "autocomplete" );
        if ( !autocomplete.options.autoSelect || autocomplete.selectedItem ) { return; }

        var matcher = new RegExp( "^" + $.ui.autocomplete.escapeRegex( $(this).val() ) + "$", "i" );
        autocomplete.widget().children( ".ui-menu-item" ).each(function() {
            var item = $( this ).data( "uiAutocompleteItem" );
            if ( matcher.test( item.label || item.value || item ) ) {
                autocomplete.selectedItem = item;
                return false;
            }
        });
        if ( autocomplete.selectedItem ) {
            autocomplete._trigger( "select", event, { item: autocomplete.selectedItem } );
        }
    });
}( jQuery ));

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