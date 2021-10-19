package it.ddcompendium;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import it.ddcompendium.customviews.ProgressBarButton;
import it.ddcompendium.customviews.TextViewButton;
import it.ddcompendium.entities.Status;
import it.ddcompendium.requests.Callback;
import it.ddcompendium.service.UsersService;
import it.ddcompendium.service.impl.UsersServiceImpl;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    // UI Components
    private EditText mEmail, mUsername, mPassword, mConfirmPassword;
    private ProgressBarButton mSignUpButton;
    private TextViewButton mLogIn;

    // Variables
    private UsersService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmail = findViewById(R.id.etEmail);
        mUsername = findViewById(R.id.etUsername);
        mPassword = findViewById(R.id.etPassword);
        mConfirmPassword = findViewById(R.id.etConfPassword);
        mSignUpButton = findViewById(R.id.btnSignUp);
        mLogIn = findViewById(R.id.yesAccount);

        mService = new UsersServiceImpl(this);

        setListeners();
    }

    private void setListeners() {
        mUsername.addTextChangedListener(this);
        mPassword.addTextChangedListener(this);
        mEmail.addTextChangedListener(this);
        mConfirmPassword.addTextChangedListener(this);
        mSignUpButton.setOnClickListener(this);
        mLogIn.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        String email = mEmail.getText().toString();
        String confPassword = mConfirmPassword.getText().toString();

        mSignUpButton.setEnabled(
                username.length() > 2 && password.length() > 1 && email.length() > 1 && confPassword.length() > 1 && password.equals(confPassword)
        );
    }

    @Override
    public void onClick(View view) {
        mService.hideKeyboard();
        if (view.getId() == mSignUpButton.getId()) {
            String username = mUsername.getText().toString();
            String password = mPassword.getText().toString();
            String email = mEmail.getText().toString();

            mService.signUp(username, email, password, new Callback<Status>() {
                @Override
                public void onSuccess(Status status) {
                    Toast.makeText(getApplicationContext(), status.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Status status) {
                    Toast.makeText(getApplicationContext(), status.getMessage(), Toast.LENGTH_SHORT).show();
                    mSignUpButton.onUpdate();
                }
            });


        } else if (view.getId() == mLogIn.getId()) {
            finish();
        }
    }
}