package org.daniils.vloerinspection.ui.loggedin;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.daniils.vloerinspection.R;
import org.daniils.vloerinspection.ui.loggedin.tabs.calendar_tab.CalendarTabFragment;
import org.daniils.vloerinspection.ui.loggedin.tabs.map_tab.MapTabFragment;

import java.util.ArrayList;
import java.util.List;

public class LoggedInActivityPagerAdapter extends FragmentPagerAdapter {

    private static class FragmentPage {
        int titleId;
        Fragment fragment;

        FragmentPage(int titleId, Fragment fragment) {
            this.titleId = titleId;
            this.fragment = fragment;
        }

        public int getTitleStringId() {
            return titleId;
        }

        public Fragment getFragment() {
            return fragment;
        }
    }

    private List<FragmentPage> pages = new ArrayList<>();
    private Context context;

    public LoggedInActivityPagerAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
        this.addPage(R.string.calendar_tab, new CalendarTabFragment());
        this.addPage(R.string.map_tab, new MapTabFragment());
    }

    private void addPage(int titleId, Fragment fragment) {
        pages.add(new FragmentPage(titleId, fragment));
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return pages.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getString(pages.get(position).getTitleStringId());
    }
}
