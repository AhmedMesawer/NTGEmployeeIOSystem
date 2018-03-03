package com.example.ilias.ntgemployeeiosystem.in_out;

import com.example.ilias.ntgemployeeiosystem.data.WorkDay;
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
        ioView.deactivateFAB();
        employeesDataSource.getEmployee(email,
                employee -> {
                    ioView.hideLoadingIndicator();
                    ioView.activateFAB();
                    ioView.showEmployee(employee);
                },
                errMsg -> {
                    ioView.hideLoadingIndicator();
                    ioView.showFailedRequestMsg(errMsg);
                });
    }

    @Override
    public void setEmployeeAttended(String email, WorkDay workDay) {
        ioView.showLoadingIndicator();
        employeesDataSource.addWorkDay(email, workDay,
                wd -> {
                    ioView.hideLoadingIndicator();
                    ioView.showSuccessAttendanceMsg(wd);
                },
                errMsg -> {
                    ioView.hideLoadingIndicator();
                    ioView.showFailedRequestMsg(errMsg);
                });
    }

    @Override
    public void setEmployeeWentOut(String email, String workDayId, WorkDay workDay) {
        employeesDataSource.setEmployeeOut(email, workDayId, workDay,
                ioView::showSuccessWentOutMsg,
                ioView::showFailedRequestMsg);
    }

}
