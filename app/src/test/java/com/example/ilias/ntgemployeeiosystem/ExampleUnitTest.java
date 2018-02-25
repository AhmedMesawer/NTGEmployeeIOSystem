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
        assertTrue(isValidNTGEmail("amesawer@ntgclarity.com"));
        assertFalse(isValidNTGEmail("ahmed+-mesawer@ntg-clarity.com"));
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