package com.capzim.capzim_profile.model;

import com.capzim.capzim_profile.entity.BankDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Johnson Andrew Siziba (sizibajohnsona@gmail.com,+263784310119)
 * @version 1.0
 * Date: 9/11/2022
 * Time: 20:35
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankDetailsResponseModel {
    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID bankId;
    private String accountName;
    private String branch;
    private String accountNumber;
    private String bankStatementFileName;
    private String bankStatementFileType;
    private String bankStatementDownloadPath;

    private boolean verified;
    private UUID verifiedBy;

    public  BankDetailsResponseModel(BankDetails bankDetails){
        this.id = bankDetails.getId();
        this.createdAt = bankDetails.getCreatedAt();
        this.updatedAt = bankDetails.getUpdatedAt();
        this.bankId = bankDetails.getBankId();
        this.accountName = bankDetails.getAccountName();
        this.branch = bankDetails.getBranch();
        this.accountName = bankDetails.getAccountName();
        this.branch = bankDetails.getBranch();
        this.accountNumber = bankDetails.getAccountNumber();
        this.bankStatementFileName = bankDetails.getBankStatementFileName();
        this.bankStatementFileType = bankDetails.getBankStatementFileType();
        this.verified = bankDetails.isVerified();
        this.verifiedBy = bankDetails.getVerifiedBy();
        this.bankStatementDownloadPath = "/api/v1/profiles/bank_details/bank_statement/download";
    }
}
