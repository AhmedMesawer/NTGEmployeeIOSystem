package com.example.ilias.ntgemployeeiosystem.data;

import java.util.Date;

/**
 * Created by ilias on 20/02/2018.
 */

public class WorkDay {
    private Date date;
    private String in;
    private String out;

    public WorkDay(Date date) {
        this.date = date;
    }

    //region setters and getters
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }
    //endregion
}
