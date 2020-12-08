package org.daniils.vloerinspection.ui.loggedin.tabs.calendar_tab;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import org.daniils.vloerinspection.R;
import org.daniils.vloerinspection.data.view.JobsView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class TableBuilder {

    private final Context context;
    private final GridLayout layout;
    private final JobsRequestResult jobsRequestResult;

    public TableBuilder(GridLayout layout, JobsRequestResult jobsRequestResult) {
        this.context = layout.getContext();
        this.layout = layout;
        this.jobsRequestResult = jobsRequestResult;
    }

    public void buildTableForPeriod(LocalDate from, LocalDate to) {
        if (this.jobsRequestResult.getJobsView() != null) {
            try {
                displayTable(from, to);
            } catch (DateTimeParseException e) {
                e.printStackTrace();
                displayError(R.string.error_building_table);
            }
        } else {
            displayError(jobsRequestResult.getErrorStringId());
        }
    }

    private void displayTable(LocalDate from, LocalDate to) throws DateTimeParseException {
        List<JobsView.Column> columns = this.jobsRequestResult.getJobsView().getColumns(from, to);
        //add(newTextView(""), 0, 0, 1, 1);
        displayDaysRow(columns);
        displayHoursColumn();
        displayColumns(columns);
    }

    private void displayDaysRow(List<JobsView.Column> columns) {
        int colIndex = 1;
        for (JobsView.Column column : columns) {
            createDayTextView(column, colIndex++);
        }
    }

    private void displayHoursColumn() {
        LocalDateTime time = LocalDate.now().atStartOfDay();
        for (int rowIndex = 1; rowIndex <= 24; rowIndex++) {
            createHoursTextView(time, rowIndex);
            time = time.plusHours(1);
        }
    }

    private void displayColumns(List<JobsView.Column> columns) {
        int colIndex = 1;
        for (JobsView.Column column : columns) {
            for (JobsView.Entry entry : column.getEntries()) {
                createEntryButton(entry, colIndex++);
            }
        }
    }

    private void createDayTextView(JobsView.Column column, int colIndex) {
        add(newTextView(column.getName()),
                colIndex, 0, 1, column.getSubcolsCount());
    }

    private void createHoursTextView(LocalDateTime time, int rowIndex) {
        String text = time.format(DateTimeFormatter.ofPattern("hh:mm"));
        add(newTextView(text),
                0, rowIndex, 1, 1);
    }

    private void createEntryButton(JobsView.Entry entry, int colIndex) {
        add(newButton(entry.getDisplayText()),
                colIndex + entry.getSubcolumn(),
                entry.getHoursFrom() + 1,
                entry.getColumnSpan(),
                1);
    }

    private TextView newTextView(String text) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setText(text);
        return textView;
    }

    private Button newButton(String text) {
        Button view = new Button(context);
        view.setGravity(Gravity.CENTER);
        view.setPadding(0 ,0,0, 0);
        view.setBackgroundColor(Color.rgb(51, 51, 51));
        view.setTextColor(Color.WHITE);
        view.setText(text);
        view.setTextSize(14);
        view.setElevation(0);
        return view;
    }

    private void add(View view, int column, int row, int columnSpan, int rowSpan) {
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.columnSpec = GridLayout.spec(column, columnSpan);
        params.rowSpec = GridLayout.spec(row, rowSpan);
        layout.addView(view, params);
    }

    private void displayError(int errorStringId) {
        add(newTextView(context.getString(errorStringId)),
                0, 0, 1, 1);
    }
}
