package com.capzim.capzim_profile.controller;

import com.capzim.capzim_profile.entity.BankDetails;
import com.capzim.capzim_profile.entity.IdDocument;
import com.capzim.capzim_profile.entity.Profile;
import com.capzim.capzim_profile.model.BankDetailsResponseModel;
import com.capzim.capzim_profile.model.ProfileResponseModel;
import com.capzim.capzim_profile.service.ProfileService;
import com.capzim.capzim_profile.utility.RolesUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    // get user bank details by user id
    @GetMapping("/bank-details/{userId}")
    public ResponseEntity<?> getUserBankDetailsByUserId(@RequestHeader("Authorization") String bearerToken, @PathVariable("userId") UUID userId){
        log.info("Inside getUserBankDetailsByUserId method of ProfileManagementController class");

        if (!rolesUtility.hasSystemAdminRole(bearerToken)){
            log.error("Role ROLE_SYSTEM_ADMIN not found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        log.info("Getting user bank details by user id: {}", userId);

        BankDetails bankDetails = profileService.getBankDetails(userId);

        BankDetailsResponseModel bankDetailsResponseModel = new BankDetailsResponseModel(bankDetails);

        return ResponseEntity.ok().body(bankDetailsResponseModel);
    }

    // get user id document by user id
    @GetMapping("/id-document/{userId}/download")
    public ResponseEntity<Resource> getUserIdDocumentByUserId(@RequestHeader("Authorization") String bearerToken, @PathVariable("userId") UUID userId){
        log.info("Inside getUserIdDocumentByUserId method of ProfileManagementController class");

        if (!rolesUtility.hasSystemAdminRole(bearerToken)){
            log.error("Role ROLE_SYSTEM_ADMIN not found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        log.info("Getting user id document by user id: {}", userId);

        Profile profile = profileService.getProfileByUserId(userId);

        IdDocument idDocument = profile.getIdDocument();

        if (idDocument == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(idDocument.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + idDocument.getIdFileName() + "\"")
                .body(new ByteArrayResource(idDocument.getIdFile()));
    }

}
