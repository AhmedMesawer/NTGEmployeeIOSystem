package com.example.ilias.ntgemployeeiosystem.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.ilias.ntgemployeeiosystem.annotations.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilias on 20/02/2018.
 */

public class Employee implements Parcelable {
    private String name;
    private String email;
    private @Team
    String team;
    private List<WorkDay> workDays;
    private double overTime;
    private double lateTime;

    public Employee(String name, String email, @Team String team) {
        this.name = name;
        this.email = email;
        this.team = team;
        this.workDays = new ArrayList<>(0);
        this.overTime = 0;
        this.lateTime = 0;
    }

    //region setters and getters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(@Team String team) {
        this.team = team;
    }

    public List<WorkDay> getWorkDays() {
        return workDays;
    }

    public void setWorkDay(WorkDay workDay) {
        this.workDays.add(workDay);
    }

    public double getOverTime() {
        return overTime;
    }

    public void setOverTime(double overTime) {
        this.overTime = overTime;
    }

    public double getLateTime() {
        return lateTime;
    }

    public void setLateTime(double lateTime) {
        this.lateTime = lateTime;
    }
    //endregion

    //region Employee Parcelable Methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.team);
        dest.writeList(this.workDays);
        dest.writeDouble(this.overTime);
        dest.writeDouble(this.lateTime);
    }

    protected Employee(Parcel in) {
        this.name = in.readString();
        this.email = in.readString();
        this.team = in.readString();
        this.workDays = new ArrayList<WorkDay>();
        in.readList(this.workDays, WorkDay.class.getClassLoader());
        this.overTime = in.readDouble();
        this.lateTime = in.readDouble();
    }

    public static final Parcelable.Creator<Employee> CREATOR = new Parcelable.Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel source) {
            return new Employee(source);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };
    //endregion
}
