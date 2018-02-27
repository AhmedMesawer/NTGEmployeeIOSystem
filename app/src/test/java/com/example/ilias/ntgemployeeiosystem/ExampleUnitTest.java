package com.example.ilias.ntgemployeeiosystem;

import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        assertTrue(isValidNTGEmail("amesawer@ntgclarity.com"));
//        assertFalse(isValidNTGEmail("ahmed+-mesawer@ntg-clarity.com"));
        String date = "Monday, February 26, 2018, week 9";
        String day = date.replaceFirst(",.+", "");
        date = date.substring(day.length() + 2);
        String month = date.replaceFirst(" .+", "");
        date = date.substring(month.length() + 1);
        String dayNo = date.replaceFirst(",.+","");
        date = date.substring(dayNo.length() + 2);
        String year = date.replaceFirst(",.+","");
        assertEquals("2018", year);
    }

    private boolean NotNullOrEmpty(String s) {
        return s != null && !s.isEmpty();
    }

    private boolean isValidNTGEmail(String s) {
        String regexp = "^[\\w-+]+(\\.[\\w]+)*@ntgclarity.com$";
        Pattern pattern = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(s).matches();
    }
}