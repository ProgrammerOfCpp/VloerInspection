package org.daniils.vloerinspection.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {

    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("firstname")
    private String firstName;
    @JsonProperty("lastname")
    private String lastname;
    @JsonProperty("vendor_store")
    private String vendorStore;

    public int getUserId() {
        return userId;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstName() {
        return firstName;
    }

    public List<Integer> getVendorStores() {
        String[] tokens = vendorStore.split(",");
        List<Integer> out = new LinkedList<>();
        for (String token : tokens) {
            out.add(Integer.parseInt(token));
        }
        return out;
    }
}