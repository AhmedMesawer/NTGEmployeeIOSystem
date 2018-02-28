package com.example.ilias.ntgemployeeiosystem.in_out;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URL;

/**
 * Created by ilias on 27/02/2018.
 */

public class DateTimeService extends IntentService {

    public static final int CURRENT_TIME_REQUEST_CODE = 1;
    public static final int CURRENT_DATE_REQUEST_CODE = 2;
    public static final String REQUEST_CODE_INTENT_KEY = "requestCode";
    public static final String RESULT_RECEIVER_INTENT_KEY = "resultReceiver";
    ResultReceiver resultReceiver;

    public DateTimeService() {
        super("DateTimeService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            resultReceiver = intent.getParcelableExtra(RESULT_RECEIVER_INTENT_KEY);
            int requestCode = intent.getIntExtra(REQUEST_CODE_INTENT_KEY, 0);
            switch (requestCode) {
                case CURRENT_DATE_REQUEST_CODE:
                    try {
                        getCurrentDateFromInternet();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case CURRENT_TIME_REQUEST_CODE:
                    try {
                        getCurrentTimeFromInternet();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:

            }
        }
    }

    public void getCurrentDateFromInternet() throws Exception {
        String url = "https://time.is/UTC+2";
        Document doc = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
        String[] tags = new String[]{
                "div[id=time_section]",
                "div[id=dd]"
        };
        Elements elements = doc.select(tags[0]);
        for (String tag : tags) {
            elements = elements.select(tag);
        }
        Bundle bundle = new Bundle();
        bundle.putString("date", elements.text());
        resultReceiver.send(1, bundle);
    }

    public void getCurrentTimeFromInternet() throws Exception {
        String url = "https://time.is/UTC+2";
        Document doc = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
        String[] tags = new String[]{
                "div[id=time_section]",
                "div[id=clock0_bg]"
        };
        Elements elements = doc.select(tags[0]);
        for (String tag : tags) {
            elements = elements.select(tag);
        }
        Bundle bundle = new Bundle();
        bundle.putString("time", elements.text());
        resultReceiver.send(1, bundle);
    }
}
