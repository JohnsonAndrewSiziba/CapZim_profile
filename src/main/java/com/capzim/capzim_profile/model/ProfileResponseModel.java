package com.capzim.capzim_profile.model;

import com.capzim.capzim_profile.entity.Profile;
import com.capzim.capzim_profile.types.ForeignOrLocal;
import com.capzim.capzim_profile.types.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @author Johnson Andrew Siziba (sizibajohnsona@gmail.com,+263784310119)
 * @version 1.0
 * Date: 5/9/2022
 * Time: 16:57
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponseModel {
    private Date dateOfBirth;

    private Gender gender;

    private String profilePicture;
    private String profilePictureFileName;

    private String signature;

    private ForeignOrLocal foreignOrLocal;

    private String nationalIdNumber;

    private String passportNumber;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String country;

    private boolean termsAndConditionsAccepted;

    private boolean enabled;

    private boolean approved;

    public ProfileResponseModel(Profile profile){
        this.dateOfBirth = profile.getDateOfBirth();
        this.gender = profile.getGender();
        this.profilePicture = null;
        this.profilePictureFileName = profile.getProfilePictureFileName();
        this.signature = null;
        this.foreignOrLocal = profile.getForeignOrLocal();
        this.nationalIdNumber = profile.getNationalIdNumber();
        this.passportNumber = profile.getPassportNumber();
        this.addressLine1 = profile.getAddressLine1();
        this.addressLine2 = profile.getAddressLine2();
        this.city = profile.getCity();
        this.country = profile.getCountry();
        this.enabled = profile.isEnabled();
        this.approved = profile.isApproved();
        this.termsAndConditionsAccepted = profile.isTermsAndConditionsAccepted();
    }
}
