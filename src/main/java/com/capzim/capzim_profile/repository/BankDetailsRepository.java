package com.capzim.capzim_profile.repository;

import com.capzim.capzim_profile.entity.BankDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Johnson Andrew Siziba (sizibajohnsona@gmail.com,+263784310119)
 * @version 1.0
 * Date: 10/11/2022
 * Time: 08:46
 */

@Repository
public interface BankDetailsRepository extends JpaRepository<BankDetails, UUID> {
}
