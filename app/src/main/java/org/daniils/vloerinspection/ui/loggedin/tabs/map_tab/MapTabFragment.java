package org.daniils.vloerinspection.ui.loggedin.tabs.map_tab;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import org.daniils.vloerinspection.R;
import org.daniils.vloerinspection.ui.loggedin.LoggedInActivityViewModel;

import java.io.IOException;

public class MapTabFragment extends Fragment implements OnMapReadyCallback {

    private MapTabViewModel viewModel;
    private TextView textViewError;
    private ProgressBar progressBar;

    public MapTabFragment() {
        super(R.layout.tab_maps);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).
                get(MapTabViewModel.class);
        textViewError = view.findViewById(R.id.textViewErrorLoadingShops);
        progressBar = view.findViewById(R.id.progressBarMapTab);

        setupMap();

        loadUserShops();
    }



    private void setupMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    private void loadUserShops() {
        progressBar.setVisibility(View.VISIBLE);
        LoggedInActivityViewModel loggedInActivityViewModel = new ViewModelProvider(requireActivity()).
                get(LoggedInActivityViewModel.class);
        loggedInActivityViewModel.getUserLoadingResult().observe(requireActivity(), userLoadingResult -> {
            if (userLoadingResult.getErrorStringId() == null) {
                viewModel.loadUserShops(userLoadingResult.getUser(), requireContext());
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        viewModel.getShopsRequestResult().observe(requireActivity(), shopsRequestResult -> {
            if (shopsRequestResult.getErrorStringId() != null) {
                textViewError.setText(shopsRequestResult.getErrorStringId());
            } else {
                try {
                    new MapStoresPlacer(requireContext(), googleMap).place(shopsRequestResult);
                } catch (IOException e) {
                    textViewError.setText(R.string.error_placing_markers);
                }
            }
            progressBar.setVisibility(View.GONE);
        });
    }
}
