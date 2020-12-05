package org.daniils.vloerinspection.ui.map;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.location.Geocoder;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import org.daniils.vloerinspection.R;
import org.daniils.vloerinspection.data.api.VloerAPI;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private MapsViewModel mapsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        final TextView textViewUserDisplayName = findViewById(R.id.textViewUserDisplayName);

        VloerAPI vloerAPI = new VloerAPI(this);

        mapsViewModel = new ViewModelProvider(this).get(MapsViewModel.class);
        mapsViewModel.getTestUserUI().observe(this, result -> textViewUserDisplayName.setText(result.getUserView().getDisplayName()));

        mapsViewModel.loadFromIntent(getIntent(), vloerAPI);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapsViewModel.getShopsRequestResult().observe(this, result -> {
            new MapStoresPlacer(this, googleMap).place(result);
        });
    }
}