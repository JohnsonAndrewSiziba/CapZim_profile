package com.capzim.capzim_profile.controller;

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
@RequestMapping("/api/v1/profiles/management")
public class ProfileManagementController {

    private final ProfileService profileService;


    private ResponseEntity<?> verifyIdDocument(){
        // TODO: 5/9/2022
        return null;
    }

    private ResponseEntity<?> verifyKycDocument(){
        // TODO: 5/9/2022
        return null;
    }


    private ResponseEntity<?> toggleIdDocumentEditing(){
        // TODO: 5/9/2022
        return null;
    }


    private ResponseEntity<?> toggleKycDocumentEditing(){
        // TODO: 5/9/2022
        return null;
    }


    private ResponseEntity<?> addCommentsToKycDocument(){
        // TODO: 5/9/2022
        return null;
    }


    private ResponseEntity<?> addCommentsToIdDocument(){
        // TODO: 5/9/2022
        return null;
    }


    private ResponseEntity<?> enableOrDisableProfile(){
        // TODO: 5/9/2022
        return null;
    }


    private ResponseEntity<?> approveProfile(){
        // TODO: 5/9/2022
        return null;
    }


    private ResponseEntity<?> addCommentsToProfile(){
        // TODO: 5/9/2022
        return null;
    }


    private ResponseEntity<?> sendNotificationToProfile(){
        // TODO: 5/9/2022
        return null;
    }






}
