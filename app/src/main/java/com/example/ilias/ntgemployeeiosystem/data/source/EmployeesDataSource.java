package com.example.ilias.ntgemployeeiosystem.data.source;

import com.example.ilias.ntgemployeeiosystem.data.Employee;
import com.example.ilias.ntgemployeeiosystem.data.WorkDay;

/**
 * Created by ilias on 20/02/2018.
 */

public interface EmployeesDataSource {
    void getEmployee(String mac);

    void addEmployee(Employee employee);

    void addWorkDay(String mac, WorkDay workDay);

    void setEmployeeOut(String mac, String date, WorkDay workDay);
}
