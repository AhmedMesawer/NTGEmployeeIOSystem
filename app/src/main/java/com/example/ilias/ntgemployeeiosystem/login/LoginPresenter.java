package com.example.ilias.ntgemployeeiosystem.login;

import com.example.ilias.ntgemployeeiosystem.data.source.EmployeesDataSource;

/**
 * Created by ilias on 25/02/2018.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View loginView;
    private EmployeesDataSource employeesDataSource;

    public LoginPresenter(LoginContract.View loginView, EmployeesDataSource employeesDataSource) {
        this.loginView = loginView;
        this.employeesDataSource = employeesDataSource;
    }

    @Override
    public void login(String email, String password) {
        employeesDataSource.getEmployee(email,
                employee -> {
                    loginView.hideLoadingIndicator();
                    loginView.navigateToEmployeeActivity(employee);
                },
                errMsg -> {
                    loginView.hideLoadingIndicator();
                    loginView.showFailedLoginMsg(errMsg);
                });
    }
}
