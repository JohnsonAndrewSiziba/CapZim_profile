package com.capzim.capzim_profile.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Johnson Andrew Siziba (sizibajohnsona@gmail.com,+263784310119)
 * @version 1.0
 */


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseModel {
    private UUID id;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String mobileNumber;
}
