package com.capzim.capzim_profile.service;

import com.capzim.capzim_profile.model.UserResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Johnson Andrew Siziba (sizibajohnsona@gmail.com,+263784310119)
 * @version 1.0
 * Date: 9/11/2022
 * Time: 16:21
 */

@Component
@RequiredArgsConstructor
public class UserInformationService {

    @LoadBalanced
    private final RestTemplate restTemplate;

    public UserResponseModel getUserDetails(String bearerToken){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", bearerToken);
        HttpEntity<?> request = new HttpEntity<Object>(headers);

        ResponseEntity<UserResponseModel> response = restTemplate.exchange(
                "http://AUTHORIZATION-SERVER/api/users/get_user_details",
                HttpMethod.GET,
                request,
                UserResponseModel.class
        );

        return response.getBody();
    }

}
