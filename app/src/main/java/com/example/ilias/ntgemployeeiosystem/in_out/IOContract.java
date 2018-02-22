package com.example.ilias.ntgemployeeiosystem.in_out;

import com.example.ilias.ntgemployeeiosystem.base.IPresenter;
import com.example.ilias.ntgemployeeiosystem.base.IView;

/**
 * Created by ilias on 21/02/2018.
 */

public interface IOContract {
    interface View extends IView<Presenter> {
        void showSuccessAttendenceMsg();
        void showFailedAttendenceMsg(String errMsg);
        void showTeam();
    }

    interface Presenter extends IPresenter{
        void setEmployeeAttended();
        void setEmployeeWentOut();
    }
}
