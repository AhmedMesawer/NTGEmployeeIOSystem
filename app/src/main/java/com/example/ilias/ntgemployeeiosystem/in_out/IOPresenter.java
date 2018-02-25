package com.example.ilias.ntgemployeeiosystem.in_out;

import com.example.ilias.ntgemployeeiosystem.data.source.EmployeesDataSource;

/**
 * Created by ilias on 21/02/2018.
 */

public class IOPresenter implements IOContract.Presenter {
    private IOContract.View ioView;
    private EmployeesDataSource employeesDataSource;

    public IOPresenter(IOContract.View ioView, EmployeesDataSource employeesDataSource) {
        this.ioView = ioView;
        this.employeesDataSource = employeesDataSource;
    }

    @Override
    public void getEmployeeIfExist(String email) {
//        employeesDataSource.getEmployee(email,);
    }

    @Override
    public void setEmployeeAttended() {

    }

    @Override
    public void setEmployeeWentOut() {

    }
}
