package com.capzim.capzim_profile.service;

import com.capzim.capzim_profile.entity.*;
import com.capzim.capzim_profile.model.*;
import com.capzim.capzim_profile.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author Johnson Andrew Siziba (sizibajohnsona@gmail.com,+263784310119)
 * @version 1.0
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    private final KycDocumentRepository kycDocumentRepository;

    @LoadBalanced
    private final RestTemplate restTemplate;

    private final TempPhoneNumberRepository tempPhoneNumberRepository;

    private final TempEmailAddressRepository tempEmailAddressRepository;

    private final NotificationService notificationService;

    private final SecondaryPhoneNumberRepository secondaryPhoneNumberRepository;

    private final SecondaryEmailRepository secondaryEmailRepository;

    private final IdDocumentRepository idDocumentRepository;

    private final BankDetailsRepository bankDetailsRepository;

    public void createProfileIfNotExist(UUID userId) {

        Profile profile = profileRepository.findProfileByUserId(userId);

        if (profile != null){
            return;
        }

//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", bearerToken);
//        HttpEntity<?> request = new HttpEntity<Object>(headers);
//
//        ResponseEntity<UserResponseModel> response = restTemplate.exchange(
//                "http://AUTHORIZATION-SERVER/api/users/get_user_details",
//                HttpMethod.GET,
//                request,
//                UserResponseModel.class
//        );
//
//        UserResponseModel userResponseModel = response.getBody();

        Profile blankProfile = new Profile();
//        assert userResponseModel != null;
        blankProfile.setUserId(userId);
        profileRepository.save(blankProfile);
    }

    public Profile updateProfile(UUID userId, EditProfileDto editProfileDto) throws Exception {

        log.info("Inside updateProfile method of ProfileService");

        Profile profile = this.getProfileByUserId(userId);

        System.out.println(editProfileDto);

        if (editProfileDto.getDateOfBirth() != null){
            SimpleDateFormat df = new SimpleDateFormat("y-M-d");
            Date dateOfBirth = df.parse(editProfileDto.getDateOfBirth());
            profile.setDateOfBirth(dateOfBirth);
        }

        if (editProfileDto.getBrokerId() != null){
            profile.setBrokerId(editProfileDto.getBrokerId());
        }

        if (editProfileDto.getGender() != null){
            profile.setGender(editProfileDto.getGender());
        }

        if (editProfileDto.getForeignOrLocal() != null){
            profile.setForeignOrLocal(editProfileDto.getForeignOrLocal());
        }

        if (editProfileDto.getNationalIdNumber() != null){
            profile.setNationalIdNumber(editProfileDto.getNationalIdNumber());
        }

        if (editProfileDto.getPassportNumber() != null){
            profile.setPassportNumber(editProfileDto.getPassportNumber());
        }

        if (editProfileDto.getAddressLine1() != null){
            profile.setAddressLine1(editProfileDto.getAddressLine1());
        }

        if (editProfileDto.getAddressLine2() != null){
            profile.setAddressLine2(editProfileDto.getAddressLine2());
        }

        if (editProfileDto.getCity() != null){
            profile.setCity(editProfileDto.getCity());
        }

        if (editProfileDto.getCountryOfResidence() != null){
            profile.setCountryOfResidence(editProfileDto.getCountryOfResidence());
        }

        if (editProfileDto.getCountry() != null){
            profile.setCountry(editProfileDto.getCountry());
        }

        profile.setTermsAndConditionsAccepted(editProfileDto.isTermsAndConditionsAccepted());

        profileRepository.save(profile);

        if (editProfileDto.getProfilePicture() != null){
            profile = this.saveProfilePicture(editProfileDto.getProfilePicture(), profile);
        }

        if (editProfileDto.getSignature() != null){
            profile = this.saveSignature(editProfileDto.getSignature(), profile);
        }

        return profile;
    }

    public Profile saveProfilePicture(MultipartFile file, Profile profile) throws Exception {
        log.info("Inside saveProfilePicture method of ProfileService");

        // TODO: 6/9/2022 Profile picture constraints
        
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {

            if (fileName.contains("..")) {
                throw new Exception("Filename contains invalid path sequence: " + fileName);
            }

            profile.setProfilePictureFile(file.getBytes());
            profile.setProfilePictureFileName(fileName);
            profile.setProfilePictureFileType(file.getContentType());

            return profileRepository.save(profile);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception("Could not save profile picture file: " + fileName);
        }
    }

    public Profile saveSignature(MultipartFile file, Profile profile) throws Exception {
        log.info("Inside saveSignature method of ProfileService");

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {

            if (fileName.contains("..")) {
                throw new Exception("Filename contains invalid path sequence: " + fileName);
            }

            profile.setSignatureFile(file.getBytes());
            profile.setSignatureFileName(fileName);
            profile.setSignatureFileType(file.getContentType());

            return profileRepository.save(profile);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception("Could not save signature file: " + fileName);
        }
    }

    public Profile getProfileByUserId(UUID userId ) {
        log.info("Inside getProfileByUserId of ProfileService");
        Profile profile = profileRepository.findProfileByUserId(userId);

        if (profile == null){
            log.info("Creating new profile for user with id: {}", userId.toString());
            profile = new Profile();
            profile.setUserId(userId);
            try {
                profileRepository.save(profile);
            }
            catch (Exception e){

            }
        }

        return profile;
    }

    public KycDocument addKycDocument(UUID userId, KycDocumentRequestDto kycDocumentRequestDto) throws Exception {
        log.info("Inside addKycDocument method of ProfileService");

        Profile profile = this.getProfileByUserId(userId);

        KycDocument kycDocument = new KycDocument();
        kycDocument.setProfile(profile);

        kycDocument.setTitle(kycDocumentRequestDto.getTitle());

        kycDocument.setDescription(kycDocumentRequestDto.getDescription());

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(kycDocumentRequestDto.getKycFile().getOriginalFilename()));

        try {

            if (fileName.contains("..")) {
                throw new Exception("Filename contains invalid path sequence: " + fileName);
            }

            kycDocument.setKycFile(kycDocumentRequestDto.getKycFile().getBytes());
            kycDocument.setKycFileName(fileName);
            kycDocument.setKycFileType(kycDocumentRequestDto.getKycFile().getContentType());

            return kycDocumentRepository.save(kycDocument);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception("Could not save kyc document file: " + fileName);
        }

    }

    public List<KycDocumentResponseModel> getAllUserKycDocuments(UUID userId) {
        Profile profile = this.getProfileByUserId(userId);
        List<KycDocumentResponseModel> kycDocumentResponseModelList = new ArrayList<>();

        for (KycDocument kycDocument: profile.getKycDocuments()){
            KycDocumentResponseModel kycDocumentResponseModel = new KycDocumentResponseModel(kycDocument);
            kycDocumentResponseModelList.add(kycDocumentResponseModel);
        }

        return kycDocumentResponseModelList;
    }

    public Profile updateProfilePicture(ProfilePictureDto profilePictureDto, UUID userId) throws Exception {
        Profile profile = this.getProfileByUserId(userId);

        profile = this.saveProfilePicture(profilePictureDto.getFile(), profile);

        return profile;

    }


    private void deleteTempPhoneNumbersIfExist(UUID userId){
        List<TempPhoneNumber> tempPhoneNumberList = tempPhoneNumberRepository.findTempPhoneNumbersByUserId(userId);
        if (!tempPhoneNumberList.isEmpty()) {
            tempPhoneNumberRepository.deleteAll(tempPhoneNumberList);
        }
    }

    private void deleteTempEmailIfExist(UUID userId){
        List<TempEmailAddress> tempEmailAddresses = tempEmailAddressRepository.findTempEmailAddressesByUserId(userId);
        if (!tempEmailAddresses.isEmpty()) {
            tempEmailAddressRepository.deleteAll(tempEmailAddresses);
        }
    }


    @Async
    public void addSaveTempSecondaryPhoneNumberAndSendToken(TempPhoneNumber tempPhoneNumber, UUID userId, String bearerToken) {
        this.deleteTempPhoneNumbersIfExist(userId);
        tempPhoneNumber.setUserId(userId);
        tempPhoneNumberRepository.save(tempPhoneNumber);

        notificationService.sendTempPhoneNumberVerification(tempPhoneNumber.getToken(), tempPhoneNumber.getPhoneNumber());

    }

    public boolean verifySecondaryPhoneNumberToken(VerificationTokenModel verificationTokenModel, UUID userId) {
        TempPhoneNumber tempPhoneNumber = tempPhoneNumberRepository.findTempPhoneNumberByTokenAndUserId(
                        verificationTokenModel.getToken(), userId
                );

        if (tempPhoneNumber == null){
            log.info("Wrong token: {}", verificationTokenModel.getToken());
            return false;
        }

        Profile profile = this.getProfileByUserId(userId);

        SecondaryPhoneNumber secondaryPhoneNumber = new SecondaryPhoneNumber();
        secondaryPhoneNumber.setProfile(profile);
        secondaryPhoneNumber.setPhoneNumber(tempPhoneNumber.getPhoneNumber());

        secondaryPhoneNumberRepository.save(secondaryPhoneNumber);

        tempPhoneNumberRepository.delete(tempPhoneNumber);

        return true;
    }

    @Async
    public void addSaveTempSecondaryEmailAndSendToken(TempEmailAddress tempEmailAddress, UUID userId, String bearerToken) {
        this.deleteTempEmailIfExist(userId);
        tempEmailAddress.setUserId(userId);
        tempEmailAddressRepository.save(tempEmailAddress);

        notificationService.sendTempEmailAddressVerification(tempEmailAddress.getToken(), tempEmailAddress.getEmail());
    }

    public boolean verifySecondaryEmailAddressToken(VerificationTokenModel verificationTokenModel, UUID userId) {
        TempEmailAddress tempEmailAddress = tempEmailAddressRepository.findTempEmailAddressByTokenAndUserId(
                verificationTokenModel.getToken(), userId
        );

        if (tempEmailAddress == null){
            log.info("Wrong token: {}", verificationTokenModel.getToken());
            return false;
        }

        Profile profile = this.getProfileByUserId(userId);

        SecondaryEmail secondaryEmail = new SecondaryEmail();
        secondaryEmail.setProfile(profile);
        secondaryEmail.setEmail(tempEmailAddress.getEmail());

        secondaryEmailRepository.save(secondaryEmail);

        tempEmailAddressRepository.delete(tempEmailAddress);

        return true;
    }

    public IdDocument addIdDocument(IdDocumentDto idDocumentDto, UUID userId) throws Exception {
        Profile profile = this.getProfileByUserId(userId);
        IdDocument idDocument = profile.getIdDocument();
        if (idDocument == null){
            idDocument = new IdDocument();
            idDocument.setProfile(profile);
            idDocument = this.saveIdDocument(idDocument, idDocumentDto);
        }
        else {
            if (!idDocument.isReadOnly()){
                idDocument = this.saveIdDocument(idDocument, idDocumentDto);
            }
        }
        return idDocument;
    }

    private IdDocument saveIdDocument(IdDocument idDocument, IdDocumentDto idDocumentDto) throws Exception {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(idDocumentDto.getFile().getOriginalFilename()));

        try {

            if (fileName.contains("..")) {
                throw new Exception("Filename contains invalid path sequence: " + fileName);
            }

            idDocument.setIdFile(idDocumentDto.getFile().getBytes());
            idDocument.setIdFileName(fileName);
            idDocument.setFileType(idDocumentDto.getFile().getContentType());

            return idDocumentRepository.save(idDocument);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception("Could not save kyc document file: " + fileName);
        }

    }

    public BankDetails getBankDetails(UUID userId) {
        log.info("Inside getBankDetails of ProfileService");

        Profile profile = this.getProfileByUserId(userId);

        BankDetails bankDetails = profile.getBankDetails();

        if (bankDetails == null){
            bankDetails = new BankDetails();
            bankDetails.setProfile(profile);
            try {
                bankDetailsRepository.save(bankDetails);
            } catch (Exception e) {log.error("Failed to save new bankDetailsObject");}
        }
        return bankDetails;
    }

    public BankDetails updateBankDetails(BankDetails bankDetails, UUID userId) {
        log.info("Inside updateBankDetails of ProfileService");
        BankDetails savedBankDetails = this.getBankDetails(userId);

        System.out.println(bankDetails);

        savedBankDetails.setBankId(bankDetails.getBankId());
        savedBankDetails.setAccountName(bankDetails.getAccountName());
        savedBankDetails.setBranch(bankDetails.getBranch());
        savedBankDetails.setAccountNumber(bankDetails.getAccountNumber());

        return bankDetailsRepository.save(savedBankDetails);

    }

    public BankDetails updateBankStatement(BankStatementRequestDto bankStatementRequestDto, UUID userId) throws Exception {
        log.info("Inside updateBankStatement of ProfileService");
        BankDetails bankDetails = this.getBankDetails(userId);

        if (bankStatementRequestDto.getFile().isEmpty()){
            return null;
        }

        MultipartFile file = bankStatementRequestDto.getFile();

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {

            if (fileName.contains("..")) {
                throw new Exception("Filename contains invalid path sequence: " + fileName);
            }

            bankDetails.setBankStatementFile(file.getBytes());
            bankDetails.setBankStatementFileName(fileName);
            bankDetails.setBankStatementFileType(file.getContentType());

            return bankDetailsRepository.save(bankDetails);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception("Could not save profile picture file: " + fileName);
        }

    }

    public Profile getUserProfileByUserId(UUID userId) {
        log.info("Inside getUserProfileByUserId of ProfileService");
        Profile profile = this.getProfileByUserId(userId);

        if (profile == null){
            return null;
        }

        return profile;
    }
}
