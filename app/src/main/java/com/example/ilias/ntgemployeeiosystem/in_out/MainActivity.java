package com.example.ilias.ntgemployeeiosystem.in_out;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity implements IOContract.View {

    public static final String EMPLOYEE_INTENT_KEY = "employee";
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
                navigateToLoginActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLoaderManager().destroyLoader(0);
    }

    @Override
    public void showSuccessAttendanceMsg(WorkDay today) {
        workDay = today;
        if (workDay != null) {
            attendanceTimeEditText.setText(workDay.getIn());
        }
    }

    @Override
    public void navigateToRegistrationActivity() {
        Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void navigateToLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showFailedAttendanceMsg(String errMsg) {
        Snackbar.make(mainLayout, errMsg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void changeFABIconAndDeactivate() {
        outFab.setImageDrawable(getResources().getDrawable(R.drawable.exit_disable));
        outFab.setBackgroundColor(getResources().getColor(R.color.background));
        outFab.setEnabled(false);
    }

    @Override
    public void activateFAB() {
        outFab.setEnabled(true);
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

    @OnClick(R.id.out_fab)
    public void onViewClicked() {
        workDay = new WorkDay();
        employee.setWorkDay(workDay);
        ioPresenter.setEmployeeAttended(employee.getEmail(), workDay);
    }
}
