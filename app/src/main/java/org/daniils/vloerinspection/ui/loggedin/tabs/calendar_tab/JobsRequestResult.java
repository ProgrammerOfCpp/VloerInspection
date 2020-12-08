package org.daniils.vloerinspection.ui.loggedin.tabs.calendar_tab;

import org.daniils.vloerinspection.data.model.Job;
import org.daniils.vloerinspection.data.view.JobsView;

import java.util.List;

public class JobsRequestResult {
    private JobsView jobsView;
    private Integer errorStringId = null;

    public JobsRequestResult(List<Job> jobsList) {
        this.jobsView = new JobsView(jobsList);
    }

    public JobsRequestResult(Integer errorStringId) {
        this.errorStringId = errorStringId;
    }

    public JobsView getJobsView() {
        return jobsView;
    }

    public Integer getErrorStringId() {
        return errorStringId;
    }
}
