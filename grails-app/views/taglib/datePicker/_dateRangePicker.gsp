<label for="${divId}"><strong>${label}</strong></label>
<div id="${divId}">
    <input type="text" class="datePicker" id="${from.id}" name="${name}/from"
           value="${from.value}" placeholder="${from.placeholder}">
    <span> - </span>
    <input type="text" class="datePicker" id="${to.id}" name="${name}/to"
           value="${to.value}" placeholder="${to.placeholder}">
    <g:javascript>
      $( function() {
        var dateFormat = "dd-mm-yy",
        options = {
            defaultDate: "+1w",
            changeMonth: true,
            changeYear: true,
            dateFormat: dateFormat
          },
          from = $( "#${from.id}" ).datepicker(options)
            .on( "change", function() {
              to.datepicker( "option", "minDate", getDate( this ) );
            }),
          to = $( "#${to.id}" ).datepicker(options)
            .on( "change", function() {
              from.datepicker( "option", "maxDate", getDate( this ) );
            });
     
        function getDate( element ) {
          var date;
          try {
            date = $.datepicker.parseDate( dateFormat, element.value );
          } catch( error ) {
            date = null;
          }
     
          return date;
        }
       } ); 
     </g:javascript>
</div>