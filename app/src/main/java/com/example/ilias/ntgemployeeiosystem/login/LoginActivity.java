package com.example.ilias.ntgemployeeiosystem.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ilias.ntgemployeeiosystem.R;
import com.example.ilias.ntgemployeeiosystem.data.Employee;
import com.example.ilias.ntgemployeeiosystem.data.WorkDay;
import com.example.ilias.ntgemployeeiosystem.in_out.MainActivity;
import com.example.ilias.ntgemployeeiosystem.registration.RegistrationActivity;
import com.example.ilias.ntgemployeeiosystem.utils.Injection;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.ilias.ntgemployeeiosystem.in_out.MainActivity.EMPLOYEE_INTENT_KEY;
import static com.example.ilias.ntgemployeeiosystem.utils.StringUtil.isNullOrEmpty;
import static com.example.ilias.ntgemployeeiosystem.utils.StringUtil.isValidNTGEmail;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    @BindView(R.id.login_email_edit_text)
    EditText loginEmailEditText;
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.password_edit_text)
    EditText passwordEditText;
    @BindView(R.id.create_account_text_view)
    TextView createAccountTextView;
    LoginContract.Presenter loginPresenter;
    @BindView(R.id.login_layout)
    ConstraintLayout loginLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenter(this,
                Injection.provideEmployeesRemoteDataSource());

    }

    @OnClick({R.id.login_button, R.id.create_account_text_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                String email = loginEmailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (isValidNTGEmail(email) && !isNullOrEmpty(password))
                    loginPresenter.login(email, password);
                if (!isValidNTGEmail(email))
                    loginEmailEditText.setError("invalid NTG email");
                if (isNullOrEmpty(password))
                    passwordEditText.setError("enter password");
                break;
            case R.id.create_account_text_view:
                navigateToRegistrationActivity();
                break;
        }
    }

    @Override
    public void showFailedLoginMsg(String errMsg) {
        Snackbar.make(loginLayout, errMsg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void navigateToEmployeeActivity(Employee employee) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra(EMPLOYEE_INTENT_KEY, employee);
        startActivity(intent);
        finish();
    }

    @Override
    public void navigateToRegistrationActivity() {
        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(intent);
        finish();
    }
}
