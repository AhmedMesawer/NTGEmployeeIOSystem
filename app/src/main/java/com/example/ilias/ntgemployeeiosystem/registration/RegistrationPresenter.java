package com.example.ilias.ntgemployeeiosystem.registration;

import com.example.ilias.ntgemployeeiosystem.data.Employee;
import com.example.ilias.ntgemployeeiosystem.data.source.EmployeesDataSource;

/**
 * Created by ilias on 21/02/2018.
 */

public class RegistrationPresenter implements RegistrationContract.Presenter {

    private RegistrationContract.View signUpView;
    private EmployeesDataSource employeesDataSource;

    public RegistrationPresenter(RegistrationContract.View signUpView, EmployeesDataSource employeesDataSource) {
        this.signUpView = signUpView;
        this.employeesDataSource = employeesDataSource;
    }

    @Override
    public void signUp(Employee employee) {
        employeesDataSource.addEmployee(employee,
                signUpView::navigateToEmployeeActivity,
                signUpView::showFailedSignUpMsg);
    }
}