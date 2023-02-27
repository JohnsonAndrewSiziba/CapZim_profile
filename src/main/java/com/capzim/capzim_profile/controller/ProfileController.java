package com.capzim.capzim_profile.controller;

import com.capzim.capzim_profile.entity.*;
import com.capzim.capzim_profile.model.*;
import com.capzim.capzim_profile.service.KycDocumentService;
import com.capzim.capzim_profile.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.core.io.Resource;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @Operation(summary = "Edit Profile")
    public ResponseEntity<ProfileResponseModel> editProfile(
                @ModelAttribute EditProfileDto editProfileDto,
                @RequestHeader("x-auth-user-id") UUID userId,
                @RequestHeader("Authorization") String bearerToken
            ) throws Exception {

        log.info("Inside editProfile of ProfileController");

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


//    @GetMapping("/profile_picture/download")
//    @Operation(summary = "Download Profile Picture")
//    public ResponseEntity<Resource> getProfilePicture(@RequestHeader("x-auth-user-id") UUID userId){
//        log.info("Inside getProfilePicture of ProfileController");
//
//        Profile profile = profileService.getProfileByUserId(userId);
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(profile.getProfilePictureFileType()))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + profile.getProfilePictureFileName() + "\"")
//                .body(new ByteArrayResource(profile.getProfilePictureFile()));
//    }

    @GetMapping("/profile_picture/download")
    @Operation(summary = "Download Profile Picture")
    public ResponseEntity<Resource> downloadProfilePicture(@RequestHeader("x-auth-user-id") UUID userId) {
        log.info("Inside downloadProfilePicture of ProfileController");

        Profile profile = profileService.getProfileByUserId(userId);

        if (profile.getProfilePictureFile() == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(profile.getProfilePictureFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + profile.getProfilePictureFileName() + "\"")
                .body(new ByteArrayResource(profile.getProfilePictureFile()));
    }

    @GetMapping("/profile_picture/{userId}/download")
    @Operation(summary = "Download Profile Picture By User Id")
    public ResponseEntity<Resource> downloadProfilePictureByUserId(@PathVariable("userId") UUID userId) {
        log.info("Inside downloadProfilePictureByUserId of ProfileController");

        Profile profile = profileService.getProfileByUserId(userId);

        if (profile.getProfilePictureFile() == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(profile.getProfilePictureFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + profile.getProfilePictureFileName() + "\"")
                .body(new ByteArrayResource(profile.getProfilePictureFile()));
    }


    @GetMapping("/profile_picture/name")
    @Operation(summary = "Get Profile Picture Name")
    public ResponseEntity<ProfilePictureResponseModel> getProfilePictureName(@RequestHeader("x-auth-user-id") UUID userId){
        log.info("Inside getProfilePictureName of ProfileController");

        Profile profile = profileService.getProfileByUserId(userId);

        return ResponseEntity.ok().body(new ProfilePictureResponseModel(profile));
    }

    @GetMapping("/id_document/name")
    @Operation(summary = "Get ID Document Name")
    public ResponseEntity<IdDocumentResponseModel> getIdDocumentName(@RequestHeader("x-auth-user-id") UUID userId){
        log.info("Inside getIdDocumentName of ProfileController");

        Profile profile = profileService.getProfileByUserId(userId);

        IdDocument idDocument = profile.getIdDocument();

        if (idDocument == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(new IdDocumentResponseModel(idDocument));
    }



    @GetMapping("/signature/download")
    @Operation(summary = "Download Signature")
    public ResponseEntity<Resource> getSignature(@RequestHeader("x-auth-user-id") UUID userId){
        log.info("Inside getSignature of ProfileController");

        Profile profile = profileService.getProfileByUserId(userId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(profile.getSignatureFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + profile.getSignatureFileName() + "\"")
                .body(new ByteArrayResource(profile.getSignatureFile()));
    }


    @GetMapping("/get_profile")
    @Operation(summary = "Get Profile Details")
    public ResponseEntity<ProfileResponseModel> getProfile(@RequestHeader("x-auth-user-id") UUID userId){
        log.info("Inside getProfile of ProfileController");

        Profile profile = profileService.getProfileByUserId(userId);
        ProfileResponseModel profileResponseModel = new ProfileResponseModel(profile);

        profileResponseModel.setProfilePicture("/api/v1/profiles/get_profile_picture");
        profileResponseModel.setSignature("/api/v1/profiles/get_signature");

        return ResponseEntity.ok().body(profileResponseModel);
    }


    @GetMapping("/kyc_documents/index")
    @Operation(summary = "Get All Kyc Documents")
    public ResponseEntity<List<KycDocumentResponseModel>> getKycDocuments(
            @RequestHeader("x-auth-user-id") UUID userId
    )
    {
        log.info("Inside getKycDocuments of ProfileController");

        return ResponseEntity.ok().body(profileService.getAllUserKycDocuments(userId));
    }


    @GetMapping("/kyc_documents/{documentId}/download")
    @Operation(summary = "Download Kyc Document")
    public ResponseEntity<Resource> downloadKycDocument(
            @PathVariable("documentId") UUID documentId,
            @RequestHeader("x-auth-user-id") UUID userId
        ){
        log.info("Inside downloadKycDocument of ProfileController");

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
    @Operation(summary = "Save KYC Document")
    public ResponseEntity<KycDocumentResponseModel> addKycDocument(
            @ModelAttribute KycDocumentRequestDto kycDocumentRequestDto,
            @RequestHeader("x-auth-user-id") UUID userId
        ) throws Exception {
        log.info("Inside addKycDocument of ProfileController");

        KycDocument kycDocument = profileService.addKycDocument(userId, kycDocumentRequestDto);
        return ResponseEntity.ok().body(new KycDocumentResponseModel(kycDocument));
    }


    @PostMapping("/profile_picture/update")
    @Operation(summary = "Update Profile Picture")
    public ResponseEntity<ProfilePictureResponseModel> changeProfilePicture(
            @ModelAttribute ProfilePictureDto profilePictureDto,
            @RequestHeader("x-auth-user-id") UUID userId
        ) throws Exception {
            log.info("Inside changeProfilePicture of ProfileController");

            Profile profile = profileService.updateProfilePicture(profilePictureDto, userId);
            ProfilePictureResponseModel profilePictureResponseModel = new ProfilePictureResponseModel(profile);

        return ResponseEntity.ok().body(profilePictureResponseModel);
    }



    @PostMapping("/add_secondary_phone_number")
    @Operation(summary = "Add Secondary Phone Number")
    public ResponseEntity<String> addSecondaryPhoneNumber(
            @Valid @RequestBody TempPhoneNumber tempPhoneNumber,
            @RequestHeader("x-auth-user-id") UUID userId,
            @RequestHeader("Authorization") String bearerToken
        )
    {
        log.info("Inside addSecondaryPhoneNumber of ProfileController");

        profileService.addSaveTempSecondaryPhoneNumberAndSendToken(tempPhoneNumber, userId, bearerToken);
        return ResponseEntity.ok().body("Verification Token Sent");
    }


    @PostMapping("/verify_secondary_phone_number_verification_token")
    @Operation(summary = "Verify Secondary Phone Number")
    public ResponseEntity<String> verifySecondaryPhoneNumberToken(@RequestBody VerificationTokenModel verificationTokenModel, @RequestHeader("x-auth-user-id") UUID userId){
        log.info("Inside verifySecondaryPhoneNumberToken of ProfileController");

        boolean isTokenVerified = profileService.verifySecondaryPhoneNumberToken(verificationTokenModel, userId);

        return isTokenVerified ? ResponseEntity.ok().body("Phone number has been verified") : ResponseEntity.badRequest().build();
    }


    @PostMapping("/add_secondary_email_address")
    @Operation(summary = "Add Secondary Email Address")
    public ResponseEntity<String> addSecondaryEmail(
            @Valid @RequestBody TempEmailAddress tempEmailAddress,
            @RequestHeader("x-auth-user-id") UUID userId,
            @RequestHeader("Authorization") String bearerToken
        ){
        log.info("Inside addSecondaryEmail of ProfileController");
        profileService.addSaveTempSecondaryEmailAndSendToken(tempEmailAddress, userId, bearerToken);
        return ResponseEntity.ok().body("Verification Token Sent");
    }


    @PostMapping("/verify_secondary_email_address_verification_token")
    @Operation(summary = "Verify Secondary Email Address")
    public ResponseEntity<String> verifySecondaryEmailToken(@RequestBody VerificationTokenModel verificationTokenModel, @RequestHeader("x-auth-user-id") UUID userId){
        log.info("Inside verifySecondaryEmailToken of ProfileController");

        boolean isTokenVerified = profileService.verifySecondaryEmailAddressToken(verificationTokenModel, userId);

        return isTokenVerified ? ResponseEntity.ok().body("Email address has been verified") : ResponseEntity.badRequest().build();
    }


    @PostMapping("/add_id_document")
    @Operation(summary = "Add ID Document")
    public ResponseEntity<IdDocumentResponseModel> addIdDocument(
            @ModelAttribute IdDocumentDto idDocumentDto,
            @RequestHeader("x-auth-user-id") UUID userId
    ) throws Exception {
        log.info("Inside addIdDocument of ProfileController");

        IdDocument idDocument = profileService.addIdDocument(idDocumentDto, userId);
        return ResponseEntity.ok().body(new IdDocumentResponseModel(idDocument));
    }


    @GetMapping("/id_document/download")
    @Operation(summary = "Download ID Document")
    public ResponseEntity<Resource> downloadIdDocument(@RequestHeader("x-auth-user-id") UUID userId){

        log.info("Inside downloadIdDocument of ProfileController");

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

    @PostMapping("/bank_details/update")
    public ResponseEntity<BankDetailsResponseModel> updateBankingDetails(@RequestBody BankDetails bankDetails, @RequestHeader("x-auth-user-id") UUID userId){
        log.info("Inside updateBankingDetails of ProfileController");

        bankDetails = profileService.updateBankDetails(bankDetails, userId);

        return ResponseEntity.ok().body(new BankDetailsResponseModel(bankDetails));
    }

    @SneakyThrows
    @PostMapping("/bank_details/bank_statement/update")
    public ResponseEntity<BankDetailsResponseModel> updateBankStatement(@ModelAttribute BankStatementRequestDto bankStatementRequestDto, @RequestHeader("x-auth-user-id") UUID userId){
        log.info("Inside updateBankStatement of ProfileController");

        BankDetails bankDetails = profileService.updateBankStatement(bankStatementRequestDto, userId);

        return ResponseEntity.ok().body(new BankDetailsResponseModel(bankDetails));
    }

    @GetMapping("/bank_details")
    public ResponseEntity<BankDetailsResponseModel> getBankingDetails(@RequestHeader("x-auth-user-id") UUID userId){
        log.info("Inside getBankingDetails of ProfileController");

        BankDetails bankDetails = profileService.getBankDetails(userId);

        return ResponseEntity.ok().body(new BankDetailsResponseModel(bankDetails));
    }

    @GetMapping("/bank_details/bank_statement/download")
    public ResponseEntity<Resource> downloadBankStatement(@RequestHeader("x-auth-user-id") UUID userId){
        log.info("Inside downloadBankStatement of ProfileController");

        BankDetails bankDetails = profileService.getBankDetails(userId);

        if (bankDetails.getBankStatementFile() == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(bankDetails.getBankStatementFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + bankDetails.getBankStatementFileName() + "\"")
                .body(new ByteArrayResource(bankDetails.getBankStatementFile()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
