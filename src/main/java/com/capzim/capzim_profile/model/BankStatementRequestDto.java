package com.capzim.capzim_profile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Johnson Andrew Siziba (sizibajohnsona@gmail.com,+263784310119)
 * @version 1.0
 * Date: 10/11/2022
 * Time: 08:26
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankStatementRequestDto {
    private MultipartFile file;
}
