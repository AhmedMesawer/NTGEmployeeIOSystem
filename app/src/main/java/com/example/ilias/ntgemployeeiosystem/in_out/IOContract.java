package com.example.ilias.ntgemployeeiosystem.in_out;

import com.example.ilias.ntgemployeeiosystem.base.IPresenter;
import com.example.ilias.ntgemployeeiosystem.base.IView;
import com.example.ilias.ntgemployeeiosystem.data.Employee;
import com.example.ilias.ntgemployeeiosystem.data.source.remote.FailedResponseCallback;
import com.example.ilias.ntgemployeeiosystem.data.source.remote.SuccessfulResponseCallback;
import com.example.ilias.ntgemployeeiosystem.data.source.remote.SuccessfulResponseWithResultCallback;

/**
 * Created by ilias on 21/02/2018.
 */

public interface IOContract {
    interface View extends IView {
        void showSuccessAttendanceMsg();

        void navigateToRegistrationActivity();

        void showFailedAttendanceMsg(String errMsg);
    }

    interface Presenter extends IPresenter {
        void getEmployeeIfExist(String email);

        void setEmployeeAttended();

        void setEmployeeWentOut();
    }
}
