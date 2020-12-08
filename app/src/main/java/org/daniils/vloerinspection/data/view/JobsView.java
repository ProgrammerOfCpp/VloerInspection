package org.daniils.vloerinspection.data.view;

import org.daniils.vloerinspection.data.model.Job;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JobsView {

    public static class Entry {
        Job job;
        int subcolumn;

        Entry(Job job) {
            this.job = job;
        }

        public int getSubcolumn() {
            return subcolumn;
        }

        public int getHoursTo() {
            return job.getInstallerTimeInterval().getHoursTo();
        }

        public int getHoursFrom() {
            return job.getInstallerTimeInterval().getHoursFrom();
        }

        public int getColumnSpan() { return getHoursFrom() - getHoursTo(); }

        public String getDisplayText() {
            return job.getCustomerFullName() + "\n" + job.getCustomerCity();
        }
    }

    public static class Column {
        List<Entry> entries = new LinkedList<>();
        LocalDate date;
        int subcolsCount = 0;

        public Column(Day day) {
            this.date = day.date;
            int[] subcolsPerHour = new int[24];
            for (Job job : day.jobs) {
                Entry entry = new Entry(job);
                for (int i = entry.getHoursFrom(); i < entry.getHoursTo(); i++) {
                    entry.subcolumn = Math.max(entry.subcolumn, subcolsPerHour[i]);
                }
                for (int i = entry.getHoursFrom(); i < entry.getHoursTo(); i++) {
                    subcolsPerHour[i] = entry.subcolumn + 1;
                }
                this.subcolsCount = Math.max(this.subcolsCount, entry.subcolumn + 1);
            }
        }

        public String getName() {
            return date.format(DateTimeFormatter.ofPattern("dd.MM"));
        }

        public List<Entry> getEntries() {
            return entries;
        }

        public int getSubcolsCount() {
            return this.subcolsCount;
        }
    }

    private static class Day {
        private List<Job> jobs = new ArrayList<>();
        private LocalDate date;

        Day(LocalDate date) {
            this.date = date;
        }
    }

    private List<Job> jobs;

    public JobsView(List<Job> jobs) {
        this.jobs = jobs;
    }

    public List<Column> getColumns(LocalDate from, LocalDate to) throws DateTimeParseException {
        Day[] days = makeDaysFromJobs(from, to);
        List<Column> columns = new LinkedList<>();
        for (Day day : days) {
            columns.add(new Column(day));
        }
        return columns;
    }

    private Day[] makeDaysFromJobs(LocalDate from, LocalDate to) throws DateTimeParseException {
        int daysCount = (int)ChronoUnit.DAYS.between(from, to);
        Day[] days = new Day[daysCount];
        for (int i = 0; i < daysCount; i++) {
            days[i] = new Day(from.plusDays(i));
        }
        for (Job job : chooseJobsByTime(from, to)) {
            int dayFrom = (int)ChronoUnit.DAYS.between(from, job.getInstallerTimeFrom());
            int dayTo = (int)ChronoUnit.DAYS.between(from, job.getInstallerTimeTo());
            for (int i = dayFrom; i < dayTo; i++) {
                if (i >= 0 && i < daysCount) {
                    days[i].jobs.add(job);
                }
            }
        }
        return days;
    }

    private List<Job> chooseJobsByTime(LocalDate from, LocalDate to) throws DateTimeParseException {
        List<Job> output = new LinkedList<>();
        for (Job job : jobs) {
            LocalDate date = LocalDate.from(job.getInstallerDate());
            if (date.compareTo(from) >= 0 && date.compareTo(to) < 0) {
                output.add(job);
            }
        }
        return output;
    }
}
