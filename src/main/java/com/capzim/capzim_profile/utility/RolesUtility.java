package com.capzim.capzim_profile.utility;

import com.capzim.capzim_profile.model.UserResponseModel;
import com.capzim.capzim_profile.service.UserInformationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author Johnson Andrew Siziba (sizibajohnsona@gmail.com,+263784310119)
 * @version 1.0
 * Date: 9/11/2022
 * Time: 16:06
 */

@RequiredArgsConstructor
@Component
@Slf4j
public class RolesUtility {

    private final UserInformationService userInformationService;

    public boolean hasSystemAdminRole(String bearerToken){

        UserResponseModel userResponseModel = userInformationService.getUserDetails(bearerToken);

        if (userResponseModel.getRoles().isEmpty()){
            return false;
        }
        return userResponseModel.getRoles().contains("ROLE_SYSTEM_ADMIN");
    }


    public UUID getUserId(String bearerToken) {
        // log
        log.info("Getting user id from bearer token");
        UserResponseModel userResponseModel = userInformationService.getUserDetails(bearerToken);
        return userResponseModel.getId();
    }

}
