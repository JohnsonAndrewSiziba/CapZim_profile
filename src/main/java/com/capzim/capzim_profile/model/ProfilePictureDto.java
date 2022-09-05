package com.capzim.capzim_profile.model;

/**
 * @author Johnson Andrew Siziba (sizibajohnsona@gmail.com,+263784310119)
 * @version 1.0
 * Date: 5/9/2022
 * Time: 16:15
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfilePictureDto {
    private MultipartFile file;
}
