package com.example.ilias.ntgemployeeiosystem.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ilias on 20/02/2018.
 */

public class Employee {
    private String name;
    private String mac;
    private @Team String team;
    private List<WorkDay> workDays;
    private double overTime;
    private double lateTime;

    public Employee(String name, String mac, @Team String team) {
        this.name = name;
        this.mac = mac;
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

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
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

    public void setWorkDays(List<WorkDay> workDays) {
        this.workDays = workDays;
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
}
