package org.daniils.vloerinspection.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Store {
    @JsonProperty("name")
    private String name;
    @JsonProperty("store_address")
    private String storeAddress;
    @JsonProperty("region")
    private String region;
    @JsonProperty("city")
    private String city;

    public String getName() {
        return name;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public String getRegion() {
        return region;
    }

    public String getCity() {
        return city;
    }
}
