package com.example.ilias.ntgemployeeiosystem.in_out;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.content.AsyncTaskLoader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;

/**
 * Created by ilias on 26/02/2018.
 */

public class TimeTask extends AsyncTaskLoader<Document> {

    public TimeTask(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public Document loadInBackground() {
        String url = "https://time.is/UTC+2";
        Document doc = null;
        try {
            doc = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }
}
