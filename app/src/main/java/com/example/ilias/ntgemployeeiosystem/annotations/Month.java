package com.example.ilias.ntgemployeeiosystem.annotations;

import android.support.annotation.StringDef;

/**
 * Created by ilias on 26/02/2018.
 */
@StringDef({
        Month.JANUARY,
        Month.FEBRUARY,
        Month.MARCH,
        Month.APRIL,
        Month.MAY,
        Month.JUNE,
        Month.JULY,
        Month.AUGUST,
        Month.SEPTEMBER,
        Month.OCTOBER,
        Month.NOVEMBER,
        Month.DECEMBER
})

public @interface Month {
    String JANUARY = "January";
    String FEBRUARY = "February";
    String MARCH = "March";
    String APRIL = "April";
    String MAY = "May";
    String JUNE = "June";
    String JULY = "July";
    String AUGUST = "August";
    String SEPTEMBER = "September";
    String OCTOBER = "October";
    String NOVEMBER = "November";
    String DECEMBER = "December";
}
