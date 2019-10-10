(function ($) {

    "use strict";

    /* ==========================================================================
                            check document is ready, then
   ========================================================================== */

    $(document).ready(function () {

        var $defaulteCounter = $(".counter");

        if ($defaulteCounter.length) {
            //enter the last menstrual period date using the date format  year, month, day
            $defaulteCounter.tictic({
                date: {
                    year: 2019,
                    month: 5,
                    day: 21
                },
                charts: {
                    disableAnimation: false
                }
            });

            $("")
        }
    });

})(window.jQuery);

