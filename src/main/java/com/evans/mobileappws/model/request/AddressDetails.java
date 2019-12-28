package com.evans.mobileappws.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDetails {
    private String city;
    private String country;
    private String streetName;
    private String postalCode;
    private String type;
}
