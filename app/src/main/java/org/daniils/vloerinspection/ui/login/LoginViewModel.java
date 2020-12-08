package org.daniils.vloerinspection.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Patterns;

import org.daniils.vloerinspection.data.api.VloerAPI;
import org.daniils.vloerinspection.R;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password, boolean remember, Context context) {
        new VloerAPI(context).getUser(username, password,
                user -> {
                    SharedPreferences.Editor editor = getSharedPreferences(context).edit();
                    editor.putBoolean(context.getString(R.string.pref_remember), remember);
                    if (remember) {
                        editor.putString(context.getString(R.string.username), username);
                        editor.putString(context.getString(R.string.password), password);
                    }
                    editor.apply();
                    loginResult.setValue(new LoginResult(user));
                },
                error -> {
                    loginResult.setValue(new LoginResult(R.string.login_failed));
                    error.printStackTrace();
                });
    }

    private SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(context.getString(R.string.app_preferences), Context.MODE_PRIVATE);
    }

    public void loadFormIfRemembered(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        boolean remember = sharedPreferences.getBoolean(context.getString(R.string.pref_remember), false);
        if (remember) {
            String username = sharedPreferences.getString(context.getString(R.string.username), "");
            String password = sharedPreferences.getString(context.getString(R.string.password), "");
            loginFormState.setValue(new LoginFormState(username, password, true));
        }
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.length() > 0;
    }
}