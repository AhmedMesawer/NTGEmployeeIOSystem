package com.example.ilias.ntgemployeeiosystem.in_out;

import com.example.ilias.ntgemployeeiosystem.base.IPresenter;
import com.example.ilias.ntgemployeeiosystem.base.IView;
import com.example.ilias.ntgemployeeiosystem.data.Employee;
import com.example.ilias.ntgemployeeiosystem.data.WorkDay;
import com.example.ilias.ntgemployeeiosystem.data.source.remote.FailedResponseCallback;
import com.example.ilias.ntgemployeeiosystem.data.source.remote.SuccessfulResponseCallback;
import com.example.ilias.ntgemployeeiosystem.data.source.remote.SuccessfulResponseWithResultCallback;

/**
 * Created by ilias on 21/02/2018.
 */

public interface IOContract {
    interface View extends IView {

        void navigateToRegistrationActivity();

        void showEmployee(Employee employee);

        void navigateToLoginActivity();

        void showSuccessAttendanceMsg(WorkDay workDay);

        void showSuccessWentOutMsg(WorkDay workDay);

        void showFailedRequestMsg(String errMsg);

        void changeFABIconAndDeactivate();

        void activateFAB();
    }

    interface Presenter extends IPresenter {
        void getEmployeeIfExist(String email);

        void setEmployeeAttended(String email, WorkDay workDay);

        void setEmployeeWentOut(String email, String workDayId, WorkDay workDay);
    }
}
