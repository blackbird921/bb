// Place your application-specific JavaScript functions and classes here
// This file is automatically included by javascript_include_tag :defaults
$(function() {
    $("#pact_plan_start_date").datepicker({dateFormat: "yy-mm-dd",
        //showOn: 'both',
        numberOfMonths: 1,
        showButtonPanel: true,
        minDate: -0, maxDate: '+12M',
        beforeShowDay: function(date){ return [date.getDay() == 1,""]}
    });
});
$(function() {
    $("#pact_plan_end_date").datepicker({dateFormat: "yy-mm-dd",
        //showOn: 'both',
        numberOfMonths: 1,
        showButtonPanel: true,
        minDate: -0, maxDate: '+12M',
        beforeShowDay: function(date){ return [date.getDay() == 0,""]}
    });
});


$(function() {
    $("#myid").datepicker({dateFormat: "yy-mm-dd",
        //showOn: 'both',
        numberOfMonths: 1,
        showButtonPanel: true,
        minDate: -0, maxDate: '+12M',
        beforeShowDay: function(date){ return [date.getDay() == 1,""]}
    });
});