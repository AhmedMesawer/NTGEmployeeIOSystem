package com.example.ilias.ntgemployeeiosystem.utils;

import java.util.regex.Pattern;

/**
 * Created by ilias on 25/02/2018.
 */

public class StringUtil {

    public static boolean isValidNTGEmail(String s) {
        String regexp = "^[\\w-+]+(\\.[\\w]+)*@ntgclarity.com$";
        Pattern pattern = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(s).matches();
    }

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
