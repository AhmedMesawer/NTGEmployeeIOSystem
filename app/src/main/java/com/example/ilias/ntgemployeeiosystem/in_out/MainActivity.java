package com.example.ilias.ntgemployeeiosystem.in_out;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ilias.ntgemployeeiosystem.R;
import com.example.ilias.ntgemployeeiosystem.registration.RegistrationActivity;
import com.example.ilias.ntgemployeeiosystem.utils.Injection;

public class MainActivity extends AppCompatActivity implements IOContract.View {

    public static final String EMPLOYEE_INTENT_KEY = "employee";
    private IOContract.Presenter ioPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ioPresenter = new IOPresenter(this, Injection.provideEmployeesRemoteDataSource());
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
    public void showSuccessAttendanceMsg() {

    }

    @Override
    public void navigateToRegistrationActivity() {
        Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showFailedAttendanceMsg(String errMsg) {

    }

    private String getNetworkMACAddress(){
        WifiManager wifiManager =
                (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            WifiInfo info = wifiManager.getConnectionInfo();
            if (info != null) {
                return info.getBSSID();
            }
        }
        return null;
    }
}
