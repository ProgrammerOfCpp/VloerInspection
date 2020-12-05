package org.daniils.vloerinspection.ui.map;

import android.app.AlertDialog;
import android.content.Context;
import android.location.Geocoder;
import android.widget.TextView;
import android.widget.Toast;

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

    public void place(ShopsRequestResult requestResult) {
        shouldShowAlert = false;
        if (requestResult.getStoreMapViews() != null) {
            placeMapViews(requestResult.getStoreMapViews());
        } else {
            Toast.makeText(context, requestResult.getErrorStringId(), Toast.LENGTH_SHORT).show();
        }
        if (shouldShowAlert) {
            showAlert();
        }
    }

    private void placeMapViews(List<StoreMapView> mapViews) {
        for (StoreMapView storeMapView : mapViews) {
            try {
                placeMapView(storeMapView);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
