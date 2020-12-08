package org.daniils.vloerinspection.data.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Job {
    @JsonProperty("installer_time_from")
    String installerTimeFrom;
    @JsonProperty("installer_time_to")
    String installerTimeTo;
    @JsonProperty("installer_date")
    String installerDate;
    @JsonProperty("installer_time_interval")
    String installerTimeInterval;
    @JsonProperty("customer_full_name")
    String customerFullName;
    @JsonProperty("customer_city")
    String customerCity;

    public static class TimeInterval {
        int hoursFrom, hoursTo;

        TimeInterval(int hoursFrom, int hoursTo) {
            this.hoursFrom = hoursFrom;
            this.hoursTo = hoursTo;
        }

        public int getHoursFrom() {
            return hoursFrom;
        }

        public int getHoursTo() {
            return hoursTo;
        }
    }

    private LocalDateTime parseDate(String date) {
        String MM = date.substring(5, 7);
        String dd = date.substring(8, 10);
        String yyyy = date.substring(0, 4);
        return LocalDateTime.of(Integer.parseInt(yyyy), Integer.parseInt(MM), Integer.parseInt(dd), 0, 0);
    }

    public LocalDateTime getInstallerTimeFrom() throws DateTimeParseException {
        return parseDate(installerTimeFrom);
    }

    public LocalDateTime getInstallerTimeTo() throws DateTimeParseException {
        return parseDate(installerTimeTo);
    }

    public LocalDateTime getInstallerDate() throws DateTimeParseException {
        String MM = installerDate.substring(0, 2);
        String dd = installerDate.substring(3, 5);
        String yyyy = installerDate.substring(6, 10);
        return LocalDateTime.of(Integer.parseInt(yyyy), Integer.parseInt(MM), Integer.parseInt(dd), 0, 0);
    }

    public TimeInterval getInstallerTimeInterval() {
        if (installerTimeInterval == null) {
            return new TimeInterval(0, 24);
        }
        // TODO!
        return null;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public String getCustomerFullName() {
        return customerFullName;
    }
}
