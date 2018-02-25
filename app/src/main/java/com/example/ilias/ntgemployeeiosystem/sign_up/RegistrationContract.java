package com.example.ilias.ntgemployeeiosystem.sign_up;

import com.example.ilias.ntgemployeeiosystem.base.IPresenter;
import com.example.ilias.ntgemployeeiosystem.base.IView;
import com.example.ilias.ntgemployeeiosystem.data.Employee;

/**
 * Created by ilias on 21/02/2018.
 */

public interface RegistrationContract {
    interface View extends IView{
        void showSuccessSignUpMsg();
        void showFailedSignUpMsg(String errMsg);
        void showInvalidEmailMsg();
        void showEmptyDataMsg();
        void navigateToEmployeeActivity(Employee employee);
    }

    interface Presenter extends IPresenter{
        void signUp(Employee employee);
    }
}
