package com.capzim.capzim_profile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Johnson Andrew Siziba (sizibajohnsona@gmail.com,+263784310119)
 * @version 1.0
 * Date: 5/9/2022
 * Time: 17:49
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KycDocumentRequestDto {
    private String title;

    private String description;

    private MultipartFile kycFile;
}
