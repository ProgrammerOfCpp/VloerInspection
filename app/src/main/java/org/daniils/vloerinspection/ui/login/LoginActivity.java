package org.daniils.vloerinspection.ui.login;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.daniils.vloerinspection.R;
import org.daniils.vloerinspection.data.model.User;
import org.daniils.vloerinspection.ui.loggedin.LoggedInActivity;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    private ProgressBar loadingProgressBar;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private CheckBox rememberCheckbox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);


        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        rememberCheckbox = findViewById(R.id.checkBoxRemember);
        final Button loginButton = findViewById(R.id.login);
        loadingProgressBar = findViewById(R.id.loading);

        loginViewModel.getLoginFormState().observe(this, (LoginFormState loginFormState) -> {
            if (loginFormState == null) {
                return;
            }
            loginButton.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getUsernameError() != null) {
                usernameEditText.setError(getString(loginFormState.getUsernameError()));
            }
            if (loginFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(loginFormState.getPasswordError()));
            }
            if (loginFormState.isFirstLoad()) {
                usernameEditText.setText(loginFormState.getUsername());
                passwordEditText.setText(loginFormState.getPassword());
                rememberCheckbox.setChecked(loginFormState.isRemember());
            }
        });

        loginViewModel.getLoginResult().observe(this, (LoginResult loginResult) -> {
            if (loginResult == null) {
                return;
            }
            loadingProgressBar.setVisibility(View.GONE);
            if (loginResult.getError() != null) {
                showLoginFailed(loginResult.getError());
            }
            if (loginResult.getSuccess() != null) {
                startMapsActivity(loginResult.getSuccess());
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                login();
            }
            return false;
        });

        loginButton.setOnClickListener(v -> login());

        loginViewModel.loadFormIfRemembered(this);
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void login() {
        loadingProgressBar.setVisibility(View.VISIBLE);
        loginViewModel.login(
                usernameEditText.getText().toString(),
                passwordEditText.getText().toString(),
                rememberCheckbox.isChecked(),
                this);
    }

    private void startMapsActivity(User user) {
        Intent intent = new Intent(this, LoggedInActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}