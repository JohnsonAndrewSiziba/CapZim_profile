package com.capzim.capzim_profile.repository;

import com.capzim.capzim_profile.entity.KycDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Johnson Andrew Siziba (sizibajohnsona@gmail.com,+263784310119)
 * @version 1.0
 */


@Repository
public interface KycDocumentRepository extends JpaRepository<KycDocument, UUID> {
    KycDocument getKycDocumentById(UUID id);
}
