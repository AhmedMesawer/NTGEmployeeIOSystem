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
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ilias.ntgemployeeiosystem.R;
import com.example.ilias.ntgemployeeiosystem.data.Employee;
import com.example.ilias.ntgemployeeiosystem.data.WorkDay;
import com.example.ilias.ntgemployeeiosystem.login.LoginActivity;
import com.example.ilias.ntgemployeeiosystem.registration.RegistrationActivity;
import com.example.ilias.ntgemployeeiosystem.utils.Injection;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.ilias.ntgemployeeiosystem.in_out.TimerService.RESULT_RECEIVER_INTENT_KEY;
import static com.example.ilias.ntgemployeeiosystem.utils.StringUtil.isNullOrEmpty;

public class MainActivity extends AppCompatActivity implements IOContract.View {

    //region Activity Fields
    public static final String EMPLOYEE_INTENT_KEY = "employee";
    public static final String YOU_CAN_GO_NOW = "youCanGoNow";
    public static final String IS_ATTENDED = "isAttended";
    public static final String SHARED_PREFERENCES_FILE_KEY = "MainSharedPreferences";
    public static final String EMPLOYEE_EMAIL_VALUE_KEY = "employeeEmailValueKey";
    public static final String WORKDAY_DATE_VALUE_KEY = "WorkdayDateValueKey";
    @BindView(R.id.out_fab)
    FloatingActionButton outFab;
    @BindView(R.id.info_text_view)
    TextView infoTextView;
    @BindView(R.id.attendance_time_edit_text)
    TextView attendanceTimeEditText;
    @BindView(R.id.main_layout)
    ConstraintLayout mainLayout;
    @BindView(R.id.main_progress_bar)
    ProgressBar mainProgressBar;
    private IOContract.Presenter ioPresenter;
    private Employee employee;
    private WorkDay workDay;
    boolean youCanGoNow;
    ServiceResultReceiver serviceResultReceiver;
    private boolean isAttended;
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
        }
//        Toast.makeText(this, getNetworkMACAddress(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        preferences = getSharedPreferences(SHARED_PREFERENCES_FILE_KEY, Context.MODE_PRIVATE);
        if (preferences != null) {
            youCanGoNow = preferences.getBoolean(YOU_CAN_GO_NOW, false);
            isAttended = preferences.getBoolean(IS_ATTENDED, false);
            if (isAttended) {
                setFABForWentOutAppearance(youCanGoNow);
            } else {
                setFABForAttendanceAppearance();
            }
            String email = preferences.getString(EMPLOYEE_EMAIL_VALUE_KEY, null);
            if (!isNullOrEmpty(email)) {
                ioPresenter.getEmployeeIfExist(email);
            } else {
                navigateToLoginActivity();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveStateInPreferences(IS_ATTENDED, isAttended);
        saveStateInPreferences(YOU_CAN_GO_NOW, youCanGoNow);
        if (employee != null) {
            saveStateInPreferences(EMPLOYEE_EMAIL_VALUE_KEY, employee.getEmail());
        }
        if (workDay != null) {
            saveStateInPreferences(WORKDAY_DATE_VALUE_KEY, workDay.getIn()/*workDay.getId()*/);
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
                saveStateInPreferences(EMPLOYEE_EMAIL_VALUE_KEY, "");
                saveStateInPreferences(WORKDAY_DATE_VALUE_KEY, "");
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
            setFABForWentOutAppearance(false);
            isAttended = true;
            youCanGoNow = false;
            startTimer();
        }
    }

    @Override
    public void showSuccessWentOutMsg(WorkDay today) {
        workDay = today;
        if (workDay != null) {
            attendanceTimeEditText.setText(workDay.getOut());
            setFABForAttendanceAppearance();
            isAttended = false;
            workDay = null;
        }
    }

    @Override
    public void showLoadingIndicator() {
        mainProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        mainProgressBar.setVisibility(View.GONE);
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
        String workdayId = preferences.getString(WORKDAY_DATE_VALUE_KEY, null);
        if (!isNullOrEmpty(workdayId)) {
            for (WorkDay workday : employee.getWorkDays()) {
                if (workday.getIn().equals(workdayId)) {
                    workDay = workday;
                }
            }
        }
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
        if (isAttended) {
            outFab.setImageDrawable(getResources().getDrawable(R.drawable.exit_disable));
            isAttended = !isAttended;
            outFab.setEnabled(false);
        } else {
            outFab.setImageDrawable(getResources().getDrawable(R.drawable.finger_print));
            isAttended = !isAttended;
        }
    }

    @Override
    public void activateFAB() {
        outFab.setEnabled(true);
    }

    @Override
    public void deactivateFAB() {
        outFab.setEnabled(false);
    }
    //endregion

    //region View Click
    @OnClick(R.id.out_fab)
    public void onViewClicked() {
        if (isOnNTGNetwork()) {
            if (!isAttended) {
                workDay = new WorkDay();
                ioPresenter.setEmployeeAttended(employee.getEmail(), workDay);
            } else {
                workDay.setOut();
                ioPresenter.setEmployeeWentOut(employee.getEmail(), workDay.getId(), workDay);
            }
        } else {
            Snackbar.make(mainLayout,
                    "You must be on NTG network",
                    Snackbar.LENGTH_LONG).show();
        }
    }
    //endregion

    //region Helper Methods

    private void saveStateInPreferences(String key, Object value) {
        preferences = getSharedPreferences(SHARED_PREFERENCES_FILE_KEY, Context.MODE_PRIVATE);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            if (value instanceof String) {
                editor.putString(key, (String) value);
            } else if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);
            }
            editor.apply();
        }
    }

    private void startTimer() {
        Intent intent = new Intent(MainActivity.this, TimerService.class);
        serviceResultReceiver = new ServiceResultReceiver();
        intent.putExtra(RESULT_RECEIVER_INTENT_KEY, serviceResultReceiver);
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

    private boolean isOnNTGNetwork() {
        List<String> acceptedMAC = Arrays.asList(getResources()
                .getStringArray(R.array.accepted_MAC));

        return acceptedMAC.contains(getNetworkMACAddress());
    }

    private void setFABForWentOutAppearance(boolean isEnabled) {
        outFab.setEnabled(isEnabled);
        if (isEnabled) {
            outFab.setImageDrawable(getResources().getDrawable(R.drawable.exit));
        } else {
            outFab.setImageDrawable(getResources().getDrawable(R.drawable.exit_disable));
        }
    }

    private void setFABForAttendanceAppearance() {
        outFab.setImageDrawable(getResources().getDrawable(R.drawable.finger_print));
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
                setFABForWentOutAppearance(youCanGoNow);
            }
        }
    }
//endregion
}
