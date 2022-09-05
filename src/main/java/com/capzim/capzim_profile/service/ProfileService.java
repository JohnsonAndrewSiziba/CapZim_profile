package com.capzim.capzim_profile.service;

import com.capzim.capzim_profile.entity.Profile;
import com.capzim.capzim_profile.model.UserResponseModel;
import com.capzim.capzim_profile.repository.KycDocumentRepository;
import com.capzim.capzim_profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.UUID;
import org.springframework.http.HttpHeaders;


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

    public void createProfileIfNotExist(String bearerToken, UUID userId) {

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
}
