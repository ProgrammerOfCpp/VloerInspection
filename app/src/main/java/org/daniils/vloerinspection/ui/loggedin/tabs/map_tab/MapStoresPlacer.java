package org.daniils.vloerinspection.ui.loggedin.tabs.map_tab;

import android.app.AlertDialog;
import android.content.Context;
import android.location.Geocoder;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;

import org.daniils.vloerinspection.R;
import org.daniils.vloerinspection.data.view.StoreMapView;

import java.io.IOException;
import java.util.List;

public class MapStoresPlacer {
    private GoogleMap googleMap;
    private Context context;
    private Geocoder geocoder;
    private StringBuilder alertBuilder;
    private boolean shouldShowAlert;

    public MapStoresPlacer(Context context, GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.context = context;
        this.geocoder = new Geocoder(context);
        alertBuilder = new StringBuilder();
    }

    public void place(ShopsRequestResult requestResult) throws IOException {
        shouldShowAlert = false;

        placeMapViews(requestResult.getStoreMapViews());

        if (shouldShowAlert) {
            showAlert();
        }
    }

    private void placeMapViews(List<StoreMapView> mapViews) throws IOException {
        for (StoreMapView storeMapView : mapViews) {
            placeMapView(storeMapView);
        }
    }

    private void showAlert() {
        new AlertDialog.Builder(context).
                setTitle(R.string.location_not_found)
                .setCancelable(true)
                .setMessage(alertBuilder.toString())
                .show();
    }

    private void placeMapView(StoreMapView storeMapView) throws IOException {
        MarkerOptions options = storeMapView.getMarkerOptions(geocoder);
        if (options == null) {
            alertBuilder.append(storeMapView.getStoreName()).append("\n");
            shouldShowAlert = true;
        } else {
            googleMap.addMarker(options);
        }
    }
}
