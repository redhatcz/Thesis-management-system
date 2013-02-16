
//typeahead fix to select value on blur
$.fn.typeahead.Constructor.prototype.blur = function (e) {
    var that = this
    setTimeout(function () { if (!that.$menu.is(':hover')) that.hide() }, 150)
    this.select()
}
