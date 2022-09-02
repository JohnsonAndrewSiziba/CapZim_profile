package com.capzim.capzim_profile.service;

import com.capzim.capzim_profile.repository.KycDocumentRepository;
import com.capzim.capzim_profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Johnson Andrew Siziba (sizibajohnsona@gmail.com,+263784310119)
 * @version 1.0
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final KycDocumentRepository kycDocumentRepository;
}
