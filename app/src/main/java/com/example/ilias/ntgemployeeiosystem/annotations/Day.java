package com.example.ilias.ntgemployeeiosystem.annotations;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by ilias on 25/02/2018.
 */

@StringDef({
        Day.SATURDAY,
        Day.SUNDAY,
        Day.MONDAY,
        Day.TUESDAY,
        Day.WEDNESDAY,
        Day.THURSDAY,
        Day.FRIDAY
})

@Retention(RetentionPolicy.RUNTIME)
public @interface Day {
    String SATURDAY = "Saturday";
    String SUNDAY = "Sunday";
    String MONDAY = "Monday";
    String TUESDAY = "Tuesday";
    String WEDNESDAY = "Wednesday";
    String THURSDAY = "Thursday";
    String FRIDAY = "Friday";
}
