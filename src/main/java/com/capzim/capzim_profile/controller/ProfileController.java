package com.capzim.capzim_profile.controller;

import com.capzim.capzim_profile.entity.Profile;
import com.capzim.capzim_profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    private ResponseEntity<?> editProfile(Profile profile){
        // TODO: 2/9/2022  Edit profile with the provided details
        return  null;
    }

    private ResponseEntity<?> getProfile(){
        // TODO: 2/9/2022 Check if profile for user exists. If it exists,
        //  return profile. Otherwise, get default values from auth server and create profile
        return null;
    }

    private ResponseEntity<?> getKycDocuments(){
        return null;
    }

    private ResponseEntity<?> addKycDocument(){
        return null;
    }

}
