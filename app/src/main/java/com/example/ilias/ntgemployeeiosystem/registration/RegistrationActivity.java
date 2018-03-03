package com.example.ilias.ntgemployeeiosystem.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.ilias.ntgemployeeiosystem.R;
import com.example.ilias.ntgemployeeiosystem.data.Employee;
import com.example.ilias.ntgemployeeiosystem.in_out.MainActivity;
import com.example.ilias.ntgemployeeiosystem.login.LoginActivity;
import com.example.ilias.ntgemployeeiosystem.utils.Injection;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.ilias.ntgemployeeiosystem.in_out.MainActivity.EMPLOYEE_INTENT_KEY;
import static com.example.ilias.ntgemployeeiosystem.utils.StringUtil.isNullOrEmpty;
import static com.example.ilias.ntgemployeeiosystem.utils.StringUtil.isValidNTGEmail;

public class RegistrationActivity extends AppCompatActivity implements RegistrationContract.View {

    @BindView(R.id.choose_team_text_view)
    TextView chooseTeamTextView;
    @BindView(R.id.login_email_edit_text)
    EditText employeeNameEditText;
    @BindView(R.id.login_button)
    Button registerNowButton;
    @BindView(R.id.email_edit_text)
    EditText emailEditText;
    RegistrationContract.Presenter registrationPresenter;
    @BindView(R.id.already_have_account_text_view)
    TextView alreadyHaveAccountTextView;
    @BindView(R.id.registration_layout)
    ConstraintLayout registrationLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        registrationPresenter = new RegistrationPresenter(this,
                Injection.provideEmployeesRemoteDataSource());
        chooseTeamTextView.setOnClickListener(view -> showChooseTeamDialog());
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void showFailedSignUpMsg(String errMsg) {
        Snackbar.make(registrationLayout, errMsg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void navigateToEmployeeActivity(Employee employee) {
        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
        intent.putExtra(EMPLOYEE_INTENT_KEY, employee);
        startActivity(intent);
        finish();
    }

    @Override
    public void navigateToLoginActivity() {
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void showChooseTeamDialog() {
        new MaterialDialog.Builder(RegistrationActivity.this)
                .title(getResources().getString(R.string.team))
                .items(getResources().getStringArray(R.array.Team))
                .titleGravity(GravityEnum.CENTER)
                .itemsGravity(GravityEnum.CENTER)
                .itemsCallback(
                        (dialog, itemView, position, text) -> {
                            chooseTeamTextView.setError(null);
                            chooseTeamTextView.setText(text);
                        })
                .show();
    }

    private Employee getValidRegistrationDataFromEmployee() {
        String name = employeeNameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String team = chooseTeamTextView.getText().toString();
        List<String> teams = Arrays.asList(getResources().getStringArray(R.array.Team));
        Employee employee = null;
        if (!isNullOrEmpty(name) && isValidNTGEmail(email) && teams.contains(team)) {
            employee = new Employee(name, email, team);
        } else {
            if (isNullOrEmpty(name))
                employeeNameEditText.setError("This field shouldn't be blank");
            if (!isValidNTGEmail(email))
                emailEditText.setError("please enter valid NTG email");
            if (!teams.contains(team))
                chooseTeamTextView.setError("please choose your team");
        }
        return employee;
    }

    @OnClick({R.id.login_button, R.id.already_have_account_text_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                Employee employee = getValidRegistrationDataFromEmployee();
                if (employee != null) {
                    registrationPresenter.signUp(employee);
                }
                break;
            case R.id.already_have_account_text_view:
                navigateToLoginActivity();
                break;
        }

    }
}
