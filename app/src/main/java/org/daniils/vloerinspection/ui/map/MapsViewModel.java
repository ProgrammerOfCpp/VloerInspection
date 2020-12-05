package org.daniils.vloerinspection.ui.map;

import android.content.Intent;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.daniils.vloerinspection.R;
import org.daniils.vloerinspection.data.api.VloerAPI;
import org.daniils.vloerinspection.data.model.Store;
import org.daniils.vloerinspection.data.model.User;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MapsViewModel extends ViewModel {

    private MutableLiveData<ShopsRequestResult> shopsRequestResult = new MutableLiveData<>();
    private MutableLiveData<UserUI> testUserUI = new MutableLiveData<>();

    public MutableLiveData<ShopsRequestResult> getShopsRequestResult() {
        return shopsRequestResult;
    }

    public MutableLiveData<UserUI> getTestUserUI() {
        return testUserUI;
    }

    public void loadFromIntent(Intent intent, VloerAPI requestsManager) {
        User user = (User)intent.getSerializableExtra("user");
        testUserUI.setValue(new UserUI(user));
        assert user != null;
        loadUserShops(user, requestsManager);
    }

    public void loadUserShops(User user, VloerAPI requestsManager) {
        List<Store> storeList = new LinkedList<>();
        AtomicInteger errorStringId = new AtomicInteger(0);
        for (Integer storeId : user.getVendorStores()) {
            synchronized (storeList) {
                requestsManager.getUserStores(storeId, storeList::addAll, error -> {
                    error.printStackTrace();
                    errorStringId.set(R.string.error_loading_shops);
                });
            }
        }
        new Thread(() -> {
            synchronized (storeList) {
                if (errorStringId.get() == 0) {
                    shopsRequestResult.postValue(new ShopsRequestResult(storeList));
                } else {
                    shopsRequestResult.postValue(new ShopsRequestResult(errorStringId.get()));
                }
            }
        }).start();
    }
}
