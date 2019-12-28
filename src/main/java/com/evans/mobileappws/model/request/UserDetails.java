package com.evans.mobileappws.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @ToString.Exclude
    private List<AddressDetails> addresses;
}
