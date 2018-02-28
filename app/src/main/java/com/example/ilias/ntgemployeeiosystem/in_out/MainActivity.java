package com.example.ilias.ntgemployeeiosystem.in_out;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.ilias.ntgemployeeiosystem.R;
import com.example.ilias.ntgemployeeiosystem.data.Employee;
import com.example.ilias.ntgemployeeiosystem.data.WorkDay;
import com.example.ilias.ntgemployeeiosystem.login.LoginActivity;
import com.example.ilias.ntgemployeeiosystem.registration.RegistrationActivity;
import com.example.ilias.ntgemployeeiosystem.utils.Injection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.ilias.ntgemployeeiosystem.in_out.DateTimeService.CURRENT_DATE_REQUEST_CODE;
import static com.example.ilias.ntgemployeeiosystem.in_out.DateTimeService.CURRENT_TIME_REQUEST_CODE;
import static com.example.ilias.ntgemployeeiosystem.in_out.DateTimeService.REQUEST_CODE_INTENT_KEY;
import static com.example.ilias.ntgemployeeiosystem.in_out.TimerService.RESULT_RECEIVER_INTENT_KEY;

public class MainActivity extends AppCompatActivity implements IOContract.View {

    //region Activity Fields
    public static final String EMPLOYEE_INTENT_KEY = "employee";
    public static final String YOU_CAN_GO_NOW = "youCanGoNow";
    public static final String IS_DATE_ADDED = "isDateAdded";
    @BindView(R.id.out_fab)
    FloatingActionButton outFab;
    @BindView(R.id.info_text_view)
    TextView infoTextView;
    @BindView(R.id.attendance_time_edit_text)
    TextView attendanceTimeEditText;
    @BindView(R.id.main_layout)
    ConstraintLayout mainLayout;
    private IOContract.Presenter ioPresenter;
    private Employee employee;
    private WorkDay workDay;
    boolean youCanGoNow = false;
    ServiceResultReceiver serviceResultReceiver;
    boolean isCurrentDateAdded = false;
    String currentDate;
    String currentTime;
    private boolean isForAttendance = true;
    SharedPreferences preferences;
    //endregion

    //region Activity LifeCycle Callbacks
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ioPresenter = new IOPresenter(this, Injection.provideEmployeesRemoteDataSource());
        if (getIntent() != null) {
            employee = getIntent().getParcelableExtra(EMPLOYEE_INTENT_KEY);
            if (employee != null) {
                editSignInSessionPreferences(employee.getEmail());
            }
        }
//        Toast.makeText(this, getNetworkMACAddress(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        preferences = getPreferences(Context.MODE_PRIVATE);
        if (preferences != null) {
            String email = preferences.getString("employeeEmail", null);
            if (email != null) {
                ioPresenter.getEmployeeIfExist(email);
            } else {
                navigateToLoginActivity();
            }
        }
//        getCurrent(CURRENT_DATE_REQUEST_CODE);
//        getCurrent(CURRENT_TIME_REQUEST_CODE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(YOU_CAN_GO_NOW, youCanGoNow);
        outState.putBoolean(IS_DATE_ADDED, isCurrentDateAdded);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState == null) {
            getCurrent(CURRENT_DATE_REQUEST_CODE);
        } else {
            isCurrentDateAdded = savedInstanceState.getBoolean(IS_DATE_ADDED);
            if (!isCurrentDateAdded) {
                getCurrent(CURRENT_DATE_REQUEST_CODE);
            }
            youCanGoNow = savedInstanceState.getBoolean(YOU_CAN_GO_NOW);
            if (youCanGoNow) {
                enableFABForWentOut();
            }
        }
    }
    //endregion

    //region Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_employee, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.time_sheet_action:

                return true;
            case R.id.sign_out_action:
                editSignInSessionPreferences(null);
                navigateToLoginActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //endregion

    //region IOView Methods
    @Override
    public void showSuccessAttendanceMsg(WorkDay today) {
        workDay = today;
        if (workDay != null) {
            attendanceTimeEditText.setText(workDay.getIn());
        }
        isCurrentDateAdded = true;
    }

    @Override
    public void showSuccessWentOutMsg(WorkDay today) {
        workDay = today;
        if (workDay != null) {
            attendanceTimeEditText.setText(workDay.getOut());
        }
    }

    @Override
    public void navigateToRegistrationActivity() {
        Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public void navigateToLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showFailedRequestMsg(String errMsg) {
        Snackbar.make(mainLayout, errMsg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void changeFABIconAndDeactivate() {
        outFab.setImageDrawable(getResources().getDrawable(R.drawable.exit_disable));
        outFab.setEnabled(false);
        isForAttendance = false;
    }

    @Override
    public void activateFAB() {
        outFab.setEnabled(true);
    }
    //endregion

    //region View Click
    @OnClick(R.id.out_fab)
    public void onViewClicked() {
        if (isForAttendance) {
            workDay = new WorkDay();
            ioPresenter.setEmployeeAttended(employee.getEmail(), workDay);
            startTimer();
        } else if (!isForAttendance) {
            workDay.setOut();
            ioPresenter.setEmployeeWentOut(employee.getEmail(), workDay.getId(), workDay);
        }
    }
    //endregion

    //region Helper Methods

    private void editSignInSessionPreferences(String sessionId) {
        preferences = getPreferences(Context.MODE_PRIVATE);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("employeeEmail", sessionId);
            editor.apply();
        }
    }

    private void startTimer() {
        Intent intent = new Intent(MainActivity.this, TimerService.class);
        serviceResultReceiver = new ServiceResultReceiver();
        intent.putExtra(RESULT_RECEIVER_INTENT_KEY, serviceResultReceiver);
        startService(intent);
    }


    private void getCurrent(int requestCode) {
        Intent intent = new Intent(MainActivity.this, DateTimeService.class);
        serviceResultReceiver = new ServiceResultReceiver();
        intent.putExtra(RESULT_RECEIVER_INTENT_KEY, serviceResultReceiver);
        intent.putExtra(REQUEST_CODE_INTENT_KEY, requestCode);
        startService(intent);
    }

    private String getNetworkMACAddress() {
        WifiManager wifiManager =
                (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            WifiInfo info = wifiManager.getConnectionInfo();
            if (info != null) {
                return info.getBSSID();
            }
        }
        return null;
    }

    private void enableFABForWentOut() {
        outFab.setImageDrawable(getResources().getDrawable(R.drawable.exit));
        outFab.setEnabled(true);
    }
//endregion

    //region ServiceResultReceiver
    class ServiceResultReceiver extends ResultReceiver {

        ServiceResultReceiver() {
            super(new Handler());
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == 0) {
                youCanGoNow = resultData.getBoolean(YOU_CAN_GO_NOW);
                if (youCanGoNow) {
                    enableFABForWentOut();
                }
            } else if (resultCode == 1) {
                currentDate = resultData.getString("date");
            } else if (resultCode == 2) {
                currentTime = resultData.getString("time");
            }
        }
    }
//endregion
}
