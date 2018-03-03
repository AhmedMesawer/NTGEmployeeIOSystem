package com.example.ilias.ntgemployeeiosystem.data;

import android.os.Parcel;
import android.os.Parcelable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

import static com.example.ilias.ntgemployeeiosystem.utils.StringUtil.isNullOrEmpty;

/**
 * Created by ilias on 20/02/2018.
 */

public class WorkDay implements Parcelable {

    private String id;
    private Date date;
    private String in;
    private String out;

    public WorkDay() {
        setId();
        this.date = getCurrentDate();
        setIn();
    }

    public String getId() {
        return id;
    }

    //region setters and getters
    private void setId() {

        String date = getCurrentDateFromInternet();
        if (!isNullOrEmpty(date))
            this.id = date;
    }

    private void setIn() {
        String time = getCurrentTimeFromInternet();
        if (!isNullOrEmpty(time))
            this.in = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIn() {
        return in;
    }

    public String getOut() {
        return out;
    }

    public void setOut() {
        String time = getCurrentTimeFromInternet();
        if (!isNullOrEmpty(time))
            this.out = time;
    }
    //endregion

    private Date getCurrentDate() {
        String dateString = getCurrentDateFromInternet();
        if (!isNullOrEmpty(dateString)) {
            String date = dateString;
            String day = date.replaceFirst(",.+", "");
            date = date.substring(day.length() + 2);
            String month = date.replaceFirst(" .+", "");
            date = date.substring(month.length() + 1);
            String dayNo = date.replaceFirst(",.+", "");
            date = date.substring(dayNo.length() + 2);
            String year = date.replaceFirst(",.+", "");
            return new Date(dayNo, day, month, year);
        }
        return null;
    }

    private String getCurrentDateFromInternet() {
        final Document[] doc = {null};
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread thread = new Thread(() -> {
            String url = "https://time.is/UTC+2";
            try {
                doc[0] = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
                countDownLatch.countDown();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
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
        Elements elements = doc[0].select(tags[0]);
        for (String tag : tags) {
            elements = elements.select(tag);
        }
        return elements.text();
    }

    private String getCurrentTimeFromInternet() {
        final Document[] doc = {null};
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread thread = new Thread(() -> {
            String url = "https://time.is/UTC+2";
            try {
                doc[0] = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
                countDownLatch.countDown();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
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
        Elements elements = doc[0].select(tags[0]);
        for (String tag : tags) {
            elements = elements.select(tag);
        }
        return elements.text();
    }

    //region Parcelable Methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeParcelable(this.date, flags);
        dest.writeString(this.in);
        dest.writeString(this.out);
    }

    protected WorkDay(Parcel in) {
        this.id = in.readString();
        this.date = in.readParcelable(Date.class.getClassLoader());
        this.in = in.readString();
        this.out = in.readString();
    }

    public static final Parcelable.Creator<WorkDay> CREATOR = new Parcelable.Creator<WorkDay>() {
        @Override
        public WorkDay createFromParcel(Parcel source) {
            return new WorkDay(source);
        }

        @Override
        public WorkDay[] newArray(int size) {
            return new WorkDay[size];
        }
    };
    //endregion
}
