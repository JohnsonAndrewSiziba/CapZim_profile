package com.capzim.capzim_profile.model;

import com.capzim.capzim_profile.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Johnson Andrew Siziba (sizibajohnsona@gmail.com,+263784310119)
 * @version 1.0
 * Date: 6/9/2022
 * Time: 08:28
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfilePictureResponseModel {
    private String fileName;
    private String downloadPath;

    public ProfilePictureResponseModel(Profile profile){
        this.fileName = profile.getProfilePictureFileName();
        this.downloadPath = "/api/v1/profiles/profile_picture/download";
    }
}
