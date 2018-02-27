package com.example.ilias.ntgemployeeiosystem.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Pattern;

/**
 * Created by ilias on 25/02/2018.
 */

public class StringUtil {

    private static Document doc = null;
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static Thread thread = new Thread(() -> {
        String url = "https://time.is/UTC+2";

        try {
            doc = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
            countDownLatch.countDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    });

    public static boolean isValidNTGEmail(String s) {
        String regexp = "^[\\w-+]+(\\.[\\w]+)*@ntgclarity.com$";
        Pattern pattern = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(s).matches();
    }

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static String getCurrentDateFromInternet() {

        try {
            thread.start();
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String[] tags = new String[]{
                "div[id=time_section]",
                "div[id=dd]"
        };
        Elements elements = doc.select(tags[0]);
        for (String tag : tags) {
            elements = elements.select(tag);
        }
        return elements.text();
    }

    public static String getCurrentTimeFromInternet() {
        try {
            thread.start();
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String[] tags = new String[]{
                "div[id=time_section]",
                "div[id=clock0_bg]"
        };
        Elements elements = doc.select(tags[0]);
        for (String tag : tags) {
            elements = elements.select(tag);
        }
        return elements.text();
    }
}
