/**
 * RichGSP javascript functions
 *
 * Dependencies: JQuery
 */

function addDynamicField(variable, size) {
    var cloneId = "#dynamic-field-" + variable + "-clone";
    var buttonId = "#add-" + variable + "-button";
    //clone last element
    var clone = $(cloneId).clone();

    //replace last element's 'clone' with listCount
    var cloneHtml = clone[0].outerHTML.replace(/clone/g, size);

    //prepend the clone before button
    $(buttonId).before(cloneHtml);

    //make it visible and enabled
    var currentDynamicFieldId = "#dynamic-field-" + variable + "-" + size;
    $(currentDynamicFieldId).removeAttr("style");
    $(currentDynamicFieldId + " :input").removeAttr("disabled");

    //change add field
    var newSize = size + 1;
    $(buttonId + " > input").attr("onclick", "addDynamicField('" + variable + "'," + newSize + ")");
}