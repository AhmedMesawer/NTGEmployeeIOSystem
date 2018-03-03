package com.example.ilias.ntgemployeeiosystem.registration;

import com.example.ilias.ntgemployeeiosystem.base.IPresenter;
import com.example.ilias.ntgemployeeiosystem.base.IView;
import com.example.ilias.ntgemployeeiosystem.data.Employee;

/**
 * Created by ilias on 21/02/2018.
 */

public interface RegistrationContract {
    interface View extends IView{
        void showFailedSignUpMsg(String errMsg);
        void navigateToEmployeeActivity(Employee employee);
        void navigateToLoginActivity();
    }

    interface Presenter extends IPresenter{
        void signUp(Employee employee);
    }
}
