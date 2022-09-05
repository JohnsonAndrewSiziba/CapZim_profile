package com.capzim.capzim_profile.service;

import com.capzim.capzim_profile.entity.KycDocument;
import com.capzim.capzim_profile.entity.Profile;
import com.capzim.capzim_profile.model.EditProfileDto;
import com.capzim.capzim_profile.model.KycDocumentRequestDto;
import com.capzim.capzim_profile.repository.KycDocumentRepository;
import com.capzim.capzim_profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;


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

        SimpleDateFormat df = new SimpleDateFormat("d-M-y");
        Date dateOfBirth = df.parse(editProfileDto.getDateOfBirth());

        profile.setDateOfBirth(dateOfBirth);

        profile.setGender(editProfileDto.getGender());

        profile.setForeignOrLocal(editProfileDto.getForeignOrLocal());

        profile.setNationalIdNumber(editProfileDto.getNationalIdNumber());

        profile.setPassportNumber(editProfileDto.getPassportNumber());

        profile.setAddressLine1(editProfileDto.getAddressLine1());

        profile.setAddressLine2(editProfileDto.getAddressLine2());

        profile.setCity(editProfileDto.getCity());

        profile.setCountry(profile.getCountry());

        profile.setTermsAndConditionsAccepted(editProfileDto.isTermsAndConditionsAccepted());

        profileRepository.save(profile);

        profile = this.saveProfilePicture(editProfileDto.getProfilePicture(), profile);

        profile = this.saveSignature(editProfileDto.getSignature(), profile);

        return profile;
    }

    public Profile saveProfilePicture(MultipartFile file, Profile profile) throws Exception {
        log.info("Inside saveProfilePicture method of ProfileService");

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

    public Profile getProfileByUserId(UUID userId) {
        log.info("Inside getProfileByUserId of ProfileService");
        Profile profile = profileRepository.findProfileByUserId(userId);

        if (profile == null){
            log.info("Creating new profile for user with id: {}", userId.toString());
            profile = new Profile();
            profile.setUserId(userId);
            profileRepository.save(profile);
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
}
