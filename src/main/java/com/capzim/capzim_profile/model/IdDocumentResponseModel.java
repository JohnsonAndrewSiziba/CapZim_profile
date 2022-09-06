package com.capzim.capzim_profile.model;

import com.capzim.capzim_profile.entity.IdDocument;
import com.capzim.capzim_profile.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Johnson Andrew Siziba (sizibajohnsona@gmail.com,+263784310119)
 * @version 1.0
 * Date: 6/9/2022
 * Time: 11:21
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdDocumentResponseModel {
    private String fileName;
    private String fileType;
    private String downloadPath;

    public IdDocumentResponseModel(IdDocument idDocument){

    }
}
