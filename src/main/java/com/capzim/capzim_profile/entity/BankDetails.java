package com.capzim.capzim_profile.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Johnson Andrew Siziba (sizibajohnsona@gmail.com,+263784310119)
 * @version 1.0
 * Date: 9/11/2022
 * Time: 20:22
 */

@Entity
@AllArgsConstructor
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class BankDetails {
    @Id
    @GeneratedValue
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID bankId;

    @OneToOne(
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    @ToString.Exclude
    private Profile profile;

    private String accountName;
    private String branch;
    private String accountNumber;

    @Lob
    private byte[] bankStatementFile;
    private String bankStatementFileName;
    private String bankStatementFileType;

    private boolean verified = false;

    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID verifiedBy;

    private boolean bankDetailsLocked = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BankDetails that = (BankDetails) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
