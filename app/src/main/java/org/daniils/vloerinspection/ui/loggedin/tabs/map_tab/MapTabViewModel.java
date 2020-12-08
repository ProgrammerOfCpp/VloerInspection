package org.daniils.vloerinspection.ui.loggedin.tabs.map_tab;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.daniils.vloerinspection.R;
import org.daniils.vloerinspection.data.api.VloerAPI;
import org.daniils.vloerinspection.data.model.Store;
import org.daniils.vloerinspection.data.model.User;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MapTabViewModel extends ViewModel {
    private MutableLiveData<ShopsRequestResult> shopsRequestResult = new MutableLiveData<>();

    public void loadUserShops(User user, Context context) {
        List<Store> storeList = new LinkedList<>();
        AtomicInteger errorStringId = new AtomicInteger(0);
        for (Integer storeId : user.getVendorStores()) {
            synchronized (storeList) {
                new VloerAPI(context).getUserStores(storeId, storeList::addAll, error -> {
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

    public MutableLiveData<ShopsRequestResult> getShopsRequestResult() {
        return shopsRequestResult;
    }
}
