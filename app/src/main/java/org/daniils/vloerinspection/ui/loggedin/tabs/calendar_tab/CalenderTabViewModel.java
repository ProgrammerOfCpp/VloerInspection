package org.daniils.vloerinspection.ui.loggedin.tabs.calendar_tab;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.daniils.vloerinspection.R;
import org.daniils.vloerinspection.data.api.VloerAPI;
import org.daniils.vloerinspection.data.model.User;

public class CalenderTabViewModel extends ViewModel {

    private MutableLiveData<JobsRequestResult> jobsRequestResult = new MutableLiveData<>();

    public MutableLiveData<JobsRequestResult> getJobsRequestResult() {
        return jobsRequestResult;
    }

    public void loadUserJobs(User user, Context context) {
        new VloerAPI(context).getLocalInstallJobs(user.getUserId(),
                response -> jobsRequestResult.setValue(new JobsRequestResult(response)),
                error -> jobsRequestResult.setValue(new JobsRequestResult(R.string.error_loading_user)));
    }
}
