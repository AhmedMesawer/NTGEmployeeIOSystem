package com.example.ilias.ntgemployeeiosystem.data;

import android.os.Parcel;
import android.os.Parcelable;

import static com.example.ilias.ntgemployeeiosystem.utils.StringUtil.getCurrentDateFromInternet;
import static com.example.ilias.ntgemployeeiosystem.utils.StringUtil.getCurrentTimeFromInternet;
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
            String year = date.replaceFirst(",.+", "");
            return new Date(dayNo, day, month, year);
        }
        return null;
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
