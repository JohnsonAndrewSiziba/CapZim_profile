package com.capzim.capzim_profile.controller;

import com.capzim.capzim_profile.entity.Profile;
import com.capzim.capzim_profile.model.ProfileDto;
import com.capzim.capzim_profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

/**
 * @author Johnson Andrew Siziba (sizibajohnsona@gmail.com,+263784310119)
 * @version 1.0
 */


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/profiles")
public class ProfileController {

    private final ProfileService profileService;


    @PostMapping("/edit_profile/{profileId}")
    private ResponseEntity<?> editProfile(
                @Valid @ModelAttribute Profile profile,
                @PathVariable("profileId") String profileId,
                @RequestHeader("x-auth-user-id") UUID userId,
                @RequestHeader("Authorization") String bearerToken
            )
    {
        profileService.createProfileIfNotExist(bearerToken, userId);

        // TODO: 5/9/2022 Check if the user has permission to edit the profile
        // TODO: 5/9/2022 Get the user ID from headers

        return  null;
    }


    private ResponseEntity<?> getProfile(){
        // TODO: 2/9/2022 Check if profile for user exists. If it exists,
        //  return profile. Otherwise, get default values from auth server and create profile
        return null;
    }


    private ResponseEntity<?> getKycDocuments(){
        // TODO: 5/9/2022 Get all of the kyc documents associated with the user
        return null;
    }


    private ResponseEntity<?> addKycDocument(){
        // TODO: 5/9/2022 Add a new kyc document
        return null;
    }


    private ResponseEntity<?> changeProfilePicture(){
        // TODO: 5/9/2022 replace the existing profile picture with the new one. Size and dimensions limits/constraints
        return null;
    }


    private ResponseEntity<?> addSecondaryPhoneNumber(){
        // TODO: 5/9/2022 Save the provided number and send verification token to the number
        // TODO: 5/9/2022 Save to temp phone numbers table / or to the SecondaryPhone Number table (I don't know).
        return null;
    }


    private ResponseEntity<?> resendSecondaryPhoneNumberToken(){
        // TODO: 5/9/2022 Send token to the previously saved number in addSecondaryPhoneNumber method above.
        return null;
    }


    private ResponseEntity<?> verifySecondaryPhoneNumberToken(){
        // TODO: 5/9/2022 Verify the provided token and, if valid, save secondary phone number.
        return null;
    }

    
    private ResponseEntity<?> addSecondaryEmail(){
        // TODO: 5/9/2022 Same with phone number
        return null;
    }


    private ResponseEntity<?> resendSecondaryEmailToken(){
        // TODO: 5/9/2022 Same with phone number
        return null;
    }


    private ResponseEntity<?> verifySecondaryEmailToken(){
        // TODO: 5/9/2022 Same with phone number
        return null;
    }

    private ResponseEntity<?> addIdDocument(){
        // TODO: 5/9/2022 Save Id document
        return null;
    }

    private ResponseEntity<?> removeIdDocument(){
        // TODO: 5/9/2022 Save Id document
        return null;
    }

}
