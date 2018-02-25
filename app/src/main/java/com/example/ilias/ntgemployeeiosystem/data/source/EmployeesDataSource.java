package com.example.ilias.ntgemployeeiosystem.data.source;

import com.example.ilias.ntgemployeeiosystem.data.Employee;
import com.example.ilias.ntgemployeeiosystem.data.WorkDay;
import com.example.ilias.ntgemployeeiosystem.data.source.remote.FailedResponseCallback;
import com.example.ilias.ntgemployeeiosystem.data.source.remote.SuccessfulResponseCallback;
import com.example.ilias.ntgemployeeiosystem.data.source.remote.SuccessfulResponseWithResultCallback;

import java.util.List;

/**
 * Created by ilias on 20/02/2018.
 */

public interface EmployeesDataSource {
    void getEmployee(String email,
                     SuccessfulResponseWithResultCallback<Employee> resultCallback,
                     FailedResponseCallback failedCallback);

    void addEmployee(Employee employee,
                     SuccessfulResponseWithResultCallback<Employee> resultCallback,
                     FailedResponseCallback failedCallback);

    void addWorkDay(String email, WorkDay workDay,
                    SuccessfulResponseCallback successCallback,
                    FailedResponseCallback failedCallback);

    void setEmployeeOut(String email, String date, WorkDay workDay,
                        SuccessfulResponseCallback successCallback,
                        FailedResponseCallback failedCallback);
}
