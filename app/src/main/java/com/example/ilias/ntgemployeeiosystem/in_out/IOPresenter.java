package com.example.ilias.ntgemployeeiosystem.in_out;

import com.example.ilias.ntgemployeeiosystem.data.source.EmployeesDataSource;

/**
 * Created by ilias on 21/02/2018.
 */

public class IOPresenter implements IOContract.Presenter {
    private IOContract.View view;
    private EmployeesDataSource employeesDataSource;

    public IOPresenter(IOContract.View view, EmployeesDataSource employeesDataSource) {
        this.view = view;
        this.employeesDataSource = employeesDataSource;
        view.setPresenter(this);
    }

    @Override
    public void setEmployeeAttended() {

    }

    @Override
    public void setEmployeeWentOut() {

    }
}
