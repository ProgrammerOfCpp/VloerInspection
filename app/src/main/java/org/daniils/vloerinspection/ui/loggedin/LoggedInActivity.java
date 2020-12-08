package org.daniils.vloerinspection.ui.loggedin;

import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.daniils.vloerinspection.R;
import org.daniils.vloerinspection.ui.loggedin.tabs.calendar_tab.CalendarTabFragment;

public class LoggedInActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_loggedin);

        loadUser();
        setupTabs();
    }

    private void loadUser() {
        LoggedInActivityViewModel loggedInActivityViewModel = new ViewModelProvider(this).get(LoggedInActivityViewModel.class);
        loggedInActivityViewModel.getUserLoadingResult().observe(this, userLoadingResult -> {
            final TextView textViewUserDisplayName = findViewById(R.id.textViewUserDisplayName);
            textViewUserDisplayName.setText(userLoadingResult.getUserView().getDisplayName());
        });
        loggedInActivityViewModel.loadUserFromIntent(getIntent());
    }


    private void setupTabs() {
        final ViewPager viewPager = findViewById(R.id.viewPager);
        LoggedInActivityPagerAdapter adapter = new LoggedInActivityPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        final TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }
}