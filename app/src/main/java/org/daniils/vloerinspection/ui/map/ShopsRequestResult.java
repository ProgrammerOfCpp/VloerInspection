package org.daniils.vloerinspection.ui.map;

import org.daniils.vloerinspection.data.model.Store;
import org.daniils.vloerinspection.data.view.StoreMapView;

import java.util.ArrayList;
import java.util.List;

public class ShopsRequestResult {
    private List<StoreMapView> storeMapViews = null;

    private Integer errorStringId = null;

    ShopsRequestResult(Integer errorStringId) {
        this.errorStringId = errorStringId;
    }

    ShopsRequestResult(List<Store> stores) {
        for (Store store : stores) {
            storeMapViews.add(new StoreMapView(store));
        }
    }

    public Integer getErrorStringId() {
        return errorStringId;
    }

    public List<StoreMapView> getStoreMapViews() {
        return storeMapViews;
    }
}
