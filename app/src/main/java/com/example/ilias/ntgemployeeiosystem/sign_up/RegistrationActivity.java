package com.example.ilias.ntgemployeeiosystem.sign_up;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.ilias.ntgemployeeiosystem.R;
import com.example.ilias.ntgemployeeiosystem.data.Employee;
import com.example.ilias.ntgemployeeiosystem.in_out.MainActivity;
import com.example.ilias.ntgemployeeiosystem.utils.Injection;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.ilias.ntgemployeeiosystem.in_out.MainActivity.EMPLOYEE_INTENT_KEY;

public class RegistrationActivity extends AppCompatActivity implements RegistrationContract.View {

    @BindView(R.id.choose_team_text_view)
    TextView chooseTeamTextView;
    @BindView(R.id.employee_name_edit_text)
    EditText employeeNameEditText;
    @BindView(R.id.register_now_button)
    Button registerNowButton;
    @BindView(R.id.email_edit_text)
    EditText emailEditText;
    RegistrationContract.Presenter registrationPresenter;

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
    public void showSuccessSignUpMsg() {

    }

    @Override
    public void showFailedSignUpMsg(String errMsg) {

    }

    @Override
    public void showInvalidEmailMsg() {

    }

    @Override
    public void showEmptyDataMsg() {

    }

    @Override
    public void navigateToEmployeeActivity(Employee employee) {
        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
        intent.putExtra(EMPLOYEE_INTENT_KEY, employee);
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
        if (isNotNullOrEmpty(name) && isValidNTGEmail(email) && teams.contains(team)) {
            employee = new Employee(name, email, team);
        } else {
            if (!isNotNullOrEmpty(name))
                employeeNameEditText.setError("This field shouldn't be blank");
            if (!isValidNTGEmail(email))
                emailEditText.setError("please enter valid NTG email");
            if (!teams.contains(team))
                chooseTeamTextView.setError("please choose your team");
        }
        return employee;
    }

    private boolean isValidNTGEmail(String s) {
        String regexp = "^[\\w-+]+(\\.[\\w]+)*@ntgclarity.com$";
        Pattern pattern = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(s).matches();
    }

    private boolean isNotNullOrEmpty(String s) {
        return s != null && !s.isEmpty();
    }

    @OnClick(R.id.register_now_button)
    public void onViewClicked() {
        Employee employee = getValidRegistrationDataFromEmployee();
        if (employee != null) {
            registrationPresenter.signUp(employee);
        }
    }
}
