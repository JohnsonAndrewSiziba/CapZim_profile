package com.capzim.capzim_profile.model;

import com.capzim.capzim_profile.entity.KycDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Johnson Andrew Siziba (sizibajohnsona@gmail.com,+263784310119)
 * @version 1.0
 * Date: 5/9/2022
 * Time: 17:53
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KycDocumentResponseModel {
    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String title;
    private String description;
    private String kycFileName;
    private String kycFileType;
    private String kycDocument;
    private boolean readOnly = false;
    private boolean verified = false;
    private String comment;

    public KycDocumentResponseModel(KycDocument kycDocument){
        this.id = kycDocument.getId();
        this.createdAt = kycDocument.getCreatedAt();
        this.updatedAt = kycDocument.getUpdatedAt();
        this.title = kycDocument.getTitle();
        this.description = kycDocument.getDescription();
        this.kycFileName = kycDocument.getKycFileName();
        this.kycFileType = kycDocument.getKycFileType();
        this.readOnly = kycDocument.isReadOnly();
        this.verified = kycDocument.isVerified();
        this.comment = kycDocument.getComment();
        this.kycDocument = "/api/v1/profiles/get_kyc_document/" + kycDocument.getId();
    }
}
