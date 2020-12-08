package org.daniils.vloerinspection.ui.loggedin.tabs.calendar_tab;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.daniils.vloerinspection.R;
import org.daniils.vloerinspection.ui.loggedin.LoggedInActivityViewModel;

import java.time.LocalDate;

public class CalendarTabFragment extends Fragment {

    private TextView textViewError;
    private CalenderTabViewModel viewModel;
    private ProgressBar progressBar;

    public CalendarTabFragment() {
        super(R.layout.tab_calendar);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewError = view.findViewById(R.id.textViewErrorLoadingShops);
        progressBar = view.findViewById(R.id.progressBarCalendarTab);
        viewModel = new ViewModelProvider(requireActivity()).get(CalenderTabViewModel.class);
        viewModel.getJobsRequestResult().observe(requireActivity(), jobsRequestResult -> {
            if (jobsRequestResult.getErrorStringId() == null) {
                GridLayout layout = view.findViewById(R.id.layout_calendar);
                LocalDate from = LocalDate.now();
                LocalDate to = from.plusDays(7);
                new TableBuilder(layout, jobsRequestResult).buildTableForPeriod(from, to);
            } else {
                textViewError.setText(jobsRequestResult.getErrorStringId());
            }
            progressBar.setVisibility(View.GONE);
        });
        loadUserJobs();
    }

    private void loadUserJobs() {
        progressBar.setVisibility(View.VISIBLE);
        LoggedInActivityViewModel loggedInActivityViewModel = new ViewModelProvider(requireActivity())
                .get(LoggedInActivityViewModel.class);
        loggedInActivityViewModel.getUserLoadingResult().observe(requireActivity(), userLoadingResult -> {
            if (userLoadingResult.getErrorStringId() == null) {
                viewModel.loadUserJobs(userLoadingResult.getUser(), requireContext());
            } else {
                textViewError.setText(R.string.error_loading_user);
            }
        });
    }
}
