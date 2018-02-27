package com.example.ilias.ntgemployeeiosystem.annotations;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by ilias on 20/02/2018.
 */

@StringDef({
        Team.MOBILE,
        Team.TESTING,
        Team.BACKEND,
        Team.IT
})

@Retention(RetentionPolicy.RUNTIME)
public @interface Team {
    String MOBILE = "Mobile";
    String TESTING = "Testing";
    String BACKEND = "Backend";
    String IT = "IT";
}
