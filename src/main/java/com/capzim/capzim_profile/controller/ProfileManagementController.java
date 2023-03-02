package com.capzim.capzim_profile.controller;

import com.capzim.capzim_profile.entity.Profile;
import com.capzim.capzim_profile.model.ProfileResponseModel;
import com.capzim.capzim_profile.service.ProfileService;
import com.capzim.capzim_profile.utility.RolesUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    private final RolesUtility rolesUtility;


    // get user profile by user id
    @GetMapping("/profiles/{id}")
    public ResponseEntity<?> getUserProfileById(@RequestHeader("Authorization") String bearerToken, @PathVariable("id") UUID userId){
        log.info("Inside getUserProfileById method of ProfileManagementController class");

        if (!rolesUtility.hasSystemAdminRole(bearerToken)){
            log.error("Role ROLE_SYSTEM_ADMIN not found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        log.info("Getting user profile by id: {}", userId);

        Profile profile = profileService.getUserProfileByUserId(userId);

        ProfileResponseModel profileResponseModel = new ProfileResponseModel(profile);

        return ResponseEntity.ok().body(profileResponseModel);
    }

}
