package org.daniils.vloerinspection.ui.loggedin;

import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.daniils.vloerinspection.data.api.VloerAPI;
import org.daniils.vloerinspection.data.model.User;

public class LoggedInActivityViewModel extends ViewModel {

    private MutableLiveData<UserLoadingResult> userLoadingResult = new MutableLiveData<>();

    public MutableLiveData<UserLoadingResult> getUserLoadingResult() {
        return userLoadingResult;
    }

    public void loadUserFromIntent(Intent intent) {
        User user = (User)intent.getSerializableExtra("user");
        userLoadingResult.setValue(new UserLoadingResult(user));
        assert user != null;
    }
}
