package org.daniils.vloerinspection.ui.login;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
class LoginFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    private boolean isDataValid;
    private String password = "";
    private String username = "";
    private boolean remember = false;
    private boolean firstLoad = false;

    LoginFormState(@Nullable Integer usernameError, @Nullable Integer passwordError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }

    LoginFormState(String username, String password, boolean remember) {
        this.username = username;
        this.password = password;
        this.remember = remember;
        firstLoad = true;
    }

    LoginFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    boolean isDataValid() {
        return isDataValid;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    boolean isFirstLoad() {
        return firstLoad;
    }

    boolean isRemember() {
        return remember;
    }
}