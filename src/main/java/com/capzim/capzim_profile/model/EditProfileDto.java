package com.capzim.capzim_profile.model;

import com.capzim.capzim_profile.types.ForeignOrLocal;
import com.capzim.capzim_profile.types.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Johnson Andrew Siziba (sizibajohnsona@gmail.com,+263784310119)
 * @version 1.0
 * Date: 5/9/2022
 * Time: 14:22
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditProfileDto {

    private String dateOfBirth;

    private Gender gender;

    private MultipartFile profilePicture;

    private MultipartFile signature;

    private ForeignOrLocal foreignOrLocal;

    private String nationalIdNumber;

    private String passportNumber;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String country;

    private boolean termsAndConditionsAccepted;

}
