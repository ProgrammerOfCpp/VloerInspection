package org.daniils.vloerinspection.data.view;

import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.daniils.vloerinspection.data.model.Store;

import java.io.IOException;
import java.util.List;

public class StoreMapView {
    private Store store;

    public StoreMapView(Store store) {
        this.store = store;
    }

    public String getStoreName() {
        return store.getName();
    }

    public String getFullStoreAddress() {
        return store.getStoreAddress() + " " + store.getRegion() + " " + store.getCity();
    }

    private LatLng getPosition(List<Address> locations) {
        Address location = locations.get(0);
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        return new LatLng(lat, lng);
    }

    public MarkerOptions getMarkerOptions(Geocoder geocoder) throws IOException {
        List<Address> locations = geocoder.getFromLocationName(getFullStoreAddress(), 1);
        if (!locations.isEmpty()) {
            return new MarkerOptions().position(getPosition(locations)).title(store.getName());
        }
        return null;
    }
}
