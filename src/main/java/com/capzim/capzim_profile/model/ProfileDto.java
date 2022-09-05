package com.capzim.capzim_profile.model;

import com.capzim.capzim_profile.types.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

/**
 * @author Johnson Andrew Siziba (sizibajohnsona@gmail.com,+263784310119)
 * @version 1.0
 * Date: 5/9/2022
 * Time: 14:22
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {
    private UUID userId;
    private Date dateOfBirth;
    private Gender gender;

}
