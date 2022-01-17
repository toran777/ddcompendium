package it.ddcompendium;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import it.ddcompendium.customviews.ProgressBarButton;
import it.ddcompendium.customviews.TextViewButton;
import it.ddcompendium.service.responses.Status;
import it.ddcompendium.entities.User;
import it.ddcompendium.requests.Callback;
import it.ddcompendium.service.UsersService;
import it.ddcompendium.service.impl.UsersServiceImpl;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    private static final String TAG = LoginActivity.class.getSimpleName();

    // UI Components
    private EditText mUsername, mPassword;
    private ProgressBarButton mLogin;
    private TextViewButton mSignUp;
    // Variables
    private UsersService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sp1 = getSharedPreferences("Login", MODE_PRIVATE);

        if (sp1.contains("id")) {
            int id = Integer.parseInt(sp1.getString("id", null));
            User user = new User();
            user.setId(id);

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        }

        mUsername = findViewById(R.id.etUsername);
        mPassword = findViewById(R.id.etPassword);
        mLogin = findViewById(R.id.btnLogin);
        mSignUp = findViewById(R.id.noAccount);

        mService = new UsersServiceImpl(this);

        setListeners();
    }

    private void setListeners() {
        mUsername.addTextChangedListener(this);
        mPassword.addTextChangedListener(this);
        mLogin.setOnClickListener(this);
        mSignUp.setOnClickListener(this);
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

        mLogin.setEnabled(username.length() > 2 && password.length() > 0);
    }

    @Override
    public void onClick(View view) {
        mService.hideKeyboard();
        if (view.getId() == mLogin.getId()) {
            Log.d(TAG, "onClick: " + "login called");
            String username = mUsername.getText().toString();
            String password = mPassword.getText().toString();

            mService.login(username, password, new Callback<User>() {
                @Override
                public void onSuccess(User user) {
                    SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("id", user.getId() + "");
                    edit.apply();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFailure(Status status) {
                    Toast.makeText(LoginActivity.this, status.getMessage(), Toast.LENGTH_SHORT).show();
                    mLogin.onUpdate(null);
                }
            });

        } else if (view.getId() == mSignUp.getId()) {
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivity(intent);
        }
    }
}