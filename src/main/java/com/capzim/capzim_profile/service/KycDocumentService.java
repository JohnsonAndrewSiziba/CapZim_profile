package com.capzim.capzim_profile.service;

import com.capzim.capzim_profile.entity.KycDocument;
import com.capzim.capzim_profile.repository.KycDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Johnson Andrew Siziba (sizibajohnsona@gmail.com,+263784310119)
 * @version 1.0
 * Date: 5/9/2022
 * Time: 18:40
 */

@RequiredArgsConstructor
@Service
public class KycDocumentService {
    private final KycDocumentRepository kycDocumentRepository;

    public KycDocument getKycDocumentById(UUID id){
        return kycDocumentRepository.getKycDocumentById(id);
    }
}
