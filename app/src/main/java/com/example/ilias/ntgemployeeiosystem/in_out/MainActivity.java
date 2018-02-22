package com.example.ilias.ntgemployeeiosystem.in_out;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.ilias.ntgemployeeiosystem.R;
import com.example.ilias.ntgemployeeiosystem.data.Team;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IOContract.View {
    @BindView(R.id.team)
    Button team;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_employee, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
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

        team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTeam();
            }
        });
    }

    @Override
    public void setPresenter(IOContract.Presenter presenter) {

    }

    @Override
    public void showSuccessAttendenceMsg() {

    }

    @Override
    public void showFailedAttendenceMsg(String errMsg) {

    }

    @Override
    public void showTeam() {
        new MaterialDialog.Builder(MainActivity.this)
                .title(getResources().getString(R.string.team))
                .items(getResources().getStringArray(R.array.Team))
                .titleGravity(GravityEnum.CENTER)
                .itemsGravity(GravityEnum.CENTER)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {

                    }
                }).show();
    }

}
