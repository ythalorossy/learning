package com.customer.customertransfomer;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Customer {
 
    private Long id;
 
    private String name;
 
    @JsonProperty("customer_name")
    public void setName(String name) {
        this.name = name;
    }
 
    @JsonProperty("name")
    public String getName() {
        return name;
    }

}
