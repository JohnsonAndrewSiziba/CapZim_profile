package com.capzim.capzim_profile.controller;

import com.capzim.capzim_profile.entity.KycDocument;
import com.capzim.capzim_profile.entity.Profile;
import com.capzim.capzim_profile.model.*;
import com.capzim.capzim_profile.service.KycDocumentService;
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

import java.util.List;
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
    private final KycDocumentService kycDocumentService;


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
        Profile profile = profileService.getProfileByUserId(userId);
        ProfileResponseModel profileResponseModel = new ProfileResponseModel(profile);

        profileResponseModel.setProfilePicture("/api/v1/profiles/get_profile_picture");
        profileResponseModel.setSignature("/api/v1/profiles/get_signature");

        return ResponseEntity.ok().body(profileResponseModel);
    }


    @GetMapping("/kyc_documents/index")
    private ResponseEntity<List<KycDocumentResponseModel>> getKycDocuments(
            @RequestHeader("x-auth-user-id") UUID userId
    ){
        return ResponseEntity.ok().body(profileService.getAllUserKycDocuments(userId));
    }


    @GetMapping("/kyc_documents/{documentId}/download")
    private ResponseEntity<Resource> downloadKycDocument(
            @PathVariable("documentId") UUID documentId,
            @RequestHeader("x-auth-user-id") UUID userId
        ){
        KycDocument kycDocument = kycDocumentService.getKycDocumentById(documentId);

        if (kycDocument == null){
            return ResponseEntity.notFound().build();
        }

        Profile profile = profileService.getProfileByUserId(userId);

        if (kycDocument.getProfile() != profile){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(kycDocument.getKycFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + kycDocument.getKycFileName() + "\"")
                .body(new ByteArrayResource(kycDocument.getKycFile()));

    }

    @PostMapping("/save_kyc_document")
    private ResponseEntity<KycDocumentResponseModel> addKycDocument(
            @ModelAttribute KycDocumentRequestDto kycDocumentRequestDto,
            @RequestHeader("x-auth-user-id") UUID userId
        ) throws Exception {
        KycDocument kycDocument = profileService.addKycDocument(userId, kycDocumentRequestDto);
        return ResponseEntity.ok().body(new KycDocumentResponseModel(kycDocument));
    }


    @PostMapping("/profile_picture/update")
    public ResponseEntity<ProfilePictureResponseModel> changeProfilePicture(
            @ModelAttribute ProfilePictureDto profilePictureDto,
            @RequestHeader("x-auth-user-id") UUID userId
        ) throws Exception {
            Profile profile = profileService.updateProfilePicture(profilePictureDto, userId);
            ProfilePictureResponseModel profilePictureResponseModel = new ProfilePictureResponseModel(profile);

        return ResponseEntity.ok().body(profilePictureResponseModel);
    }


    @GetMapping("/profile_picture/download")
    public ResponseEntity<Resource> downloadProfilePicture(@RequestHeader("x-auth-user-id") UUID userId) {
            Profile profile = profileService.getProfileByUserId(userId);

            if (profile.getProfilePictureFile() == null){
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(profile.getProfilePictureFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + profile.getProfilePictureFileName() + "\"")
                    .body(new ByteArrayResource(profile.getProfilePictureFile()));

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
