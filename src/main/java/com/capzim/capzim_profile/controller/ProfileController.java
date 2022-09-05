package com.capzim.capzim_profile.controller;

import com.capzim.capzim_profile.entity.Profile;
import com.capzim.capzim_profile.model.EditProfileDto;
import com.capzim.capzim_profile.model.KycDocumentRequestDto;
import com.capzim.capzim_profile.model.ProfileResponseModel;
import com.capzim.capzim_profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.core.io.Resource;


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


    @PostMapping("/edit_profile")
    private ResponseEntity<ProfileResponseModel> editProfile(
                @ModelAttribute EditProfileDto editProfileDto,
                @RequestHeader("x-auth-user-id") UUID userId,
                @RequestHeader("Authorization") String bearerToken
            ) throws Exception {
        Profile profile = profileService.updateProfile(userId, editProfileDto);

        ProfileResponseModel profileResponseModel = new ProfileResponseModel(profile);

        String profilePictureUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/profiles/get_profile_picture")
                .path(profile.getId().toString())
                .toUriString();

        String signatureUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/profiles/get_signature")
                .path(profile.getId().toString())
                .toUriString();

        profileResponseModel.setProfilePicture("/api/v1/profiles/get_profile_picture");
        profileResponseModel.setSignature("/api/v1/profiles/get_signature");

        return ResponseEntity.ok().body(profileResponseModel);
    }


    @GetMapping("/get_profile_picture")
    public ResponseEntity<Resource> getProfilePicture(@RequestHeader("x-auth-user-id") UUID userId){
        Profile profile = profileService.getProfileByUserId(userId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(profile.getProfilePictureFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + profile.getProfilePictureFileName() + "\"")
                .body(new ByteArrayResource(profile.getProfilePictureFile()));
    }

    @GetMapping("/get_signature")
    public ResponseEntity<Resource> getSignature(@RequestHeader("x-auth-user-id") UUID userId){
        Profile profile = profileService.getProfileByUserId(userId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(profile.getSignatureFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + profile.getSignatureFileName() + "\"")
                .body(new ByteArrayResource(profile.getSignatureFile()));
    }



    @GetMapping("/get_profile")
    private ResponseEntity<ProfileResponseModel> getProfile(@RequestHeader("x-auth-user-id") UUID userId){
        Profile profile = profileService.getUserProfile(userId);
        ProfileResponseModel profileResponseModel = new ProfileResponseModel(profile);

        profileResponseModel.setProfilePicture("/api/v1/profiles/get_profile_picture");
        profileResponseModel.setSignature("/api/v1/profiles/get_signature");

        return ResponseEntity.ok().body(profileResponseModel);
    }


    private ResponseEntity<?> getKycDocuments(){
        // TODO: 5/9/2022 Get all of the kyc documents associated with the user

        return null;
    }


    private ResponseEntity<?> addKycDocument(
            @ModelAttribute KycDocumentRequestDto kycDocumentRequestDto,
            @RequestHeader("x-auth-user-id") UUID userId
        )
    {
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
