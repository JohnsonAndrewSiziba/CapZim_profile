package com.capzim.capzim_profile.repository;

import com.capzim.capzim_profile.entity.TempPhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author Johnson Andrew Siziba (sizibajohnsona@gmail.com,+263784310119)
 * @version 1.0
 */

@Repository
public interface TempPhoneNumberRepository extends JpaRepository<TempPhoneNumber, UUID> {
    List<TempPhoneNumber> findTempPhoneNumberByUserId(UUID userId);

    TempPhoneNumber findTempPhoneNumberByTokenAndUserId(String token, UUID userId);

    List<TempPhoneNumber> findTempPhoneNumbersByUserId(UUID userId);
}
