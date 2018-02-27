package com.example.ilias.ntgemployeeiosystem.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.ilias.ntgemployeeiosystem.annotations.Day;
import com.example.ilias.ntgemployeeiosystem.annotations.Month;

/**
 * Created by ilias on 26/02/2018.
 */

public class Date implements Parcelable {

    private String dayNo;
    @Day
    private String day;
    @Month
    private String month;
    private String year;

    public Date(String dayNo, String day, String month, String year) {

        this.dayNo = dayNo;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    //region Getters
    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public String getDayNo() {
        return dayNo;
    }
    //endregion

    //region Parcelable Methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.dayNo);
        dest.writeString(this.day);
        dest.writeString(this.month);
        dest.writeString(this.year);
    }

    protected Date(Parcel in) {
        this.dayNo = in.readString();
        this.day = in.readString();
        this.month = in.readString();
        this.year = in.readString();
    }

    public static final Parcelable.Creator<Date> CREATOR = new Parcelable.Creator<Date>() {
        @Override
        public Date createFromParcel(Parcel source) {
            return new Date(source);
        }

        @Override
        public Date[] newArray(int size) {
            return new Date[size];
        }
    };
    //endregion
}
