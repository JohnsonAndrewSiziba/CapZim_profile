package com.capzim.capzim_profile.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Johnson Andrew Siziba (sizibajohnsona@gmail.com,+263784310119)
 * @version 1.0
 */

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Profile {
    @Id
    @GeneratedValue
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;

    @Column(unique = true)
    private UUID userId;

    private String foreNames;

    private String surname;

    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private ForeignOrLocal foreignOrLocal;


    private String nationalId;

    private String passportNumber;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String country;

    private String telephone;

    private String cellphoneNumber;

    private String emailAddress;

    // TODO: 2/9/2022 Add this in investor management service
//    @Enumerated(EnumType.STRING)
//    private PrimaryOrJoint primaryOrJoint = PrimaryOrJoint.PRIMARY;

    private String pathToSignature;


    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Profile profile = (Profile) o;
        return id != null && Objects.equals(id, profile.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}


enum Gender {
    MALE,FEMALE
}

enum ForeignOrLocal {
    FOREIGN,LOCAL
}

enum PrimaryOrJoint {
    PRIMARY,JOINT
}



