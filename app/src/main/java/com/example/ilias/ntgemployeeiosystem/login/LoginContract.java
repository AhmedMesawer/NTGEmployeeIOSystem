package com.example.ilias.ntgemployeeiosystem.login;

import com.example.ilias.ntgemployeeiosystem.base.IPresenter;
import com.example.ilias.ntgemployeeiosystem.base.IView;
import com.example.ilias.ntgemployeeiosystem.data.Employee;

/**
 * Created by ilias on 21/02/2018.
 */

public interface LoginContract {
    interface View extends IView {

        void showFailedLoginMsg(String errMsg);

        void navigateToEmployeeActivity(Employee employee);

        void navigateToRegistrationActivity();
    }

    interface Presenter extends IPresenter {
        void login(String email, String password);
    }
}
