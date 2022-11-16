package com.capzim.capzim_profile.entity;

import com.capzim.capzim_profile.types.ForeignOrLocal;
import com.capzim.capzim_profile.types.Gender;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
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
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID userId;

    @NotBlank(message = "Date of Birth is Mandatory")
    private Date dateOfBirth;

    @NotBlank(message = "Gender is Mandatory")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotBlank(message = "Foreign/Local is Mandatory")
    @Enumerated(EnumType.STRING)
    private ForeignOrLocal foreignOrLocal;

    @NotBlank(message = "National Id Number is Mandatory")
    private String nationalIdNumber;

    private String passportNumber;

    @NotBlank(message = "Address Line 1 is Mandatory")
    private String addressLine1;

    private String addressLine2;

    @NotBlank(message = "City is Mandatory")
    private String city;

    @NotBlank(message = "Country of Residence is Mandatory")
    private String country;

    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID brokerId;

    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID investorId;

    // TODO: 2/9/2022 Add this in investor management service
//    @Enumerated(EnumType.STRING)
//    private PrimaryOrJoint primaryOrJoint = PrimaryOrJoint.PRIMARY;

    @NotBlank(message = "Signature is Mandatory")
    @Lob
    private byte[] signatureFile;
    private String signatureFileName;
    private String signatureFileType;

    @Lob
    private byte[] profilePictureFile;
    private String profilePictureFileName;
    private String profilePictureFileType;

    private boolean enabled = false;

    private boolean approved;

    private String comment;

    private UUID approvedBy;

    private boolean termsAndConditionsAccepted = true;

    private String countryOfResidence;

    @OneToMany(
            mappedBy = "profile",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JsonIgnoreProperties("profile")
    @ToString.Exclude
    private List<KycDocument> kycDocuments;


    @OneToMany(
            mappedBy = "profile",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JsonIgnoreProperties("profile")
    @ToString.Exclude
    private List<SecondaryPhoneNumber> secondaryPhoneNumbers;


    @OneToMany(
            mappedBy = "profile",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JsonIgnoreProperties("profile")
    @ToString.Exclude
    private List<SecondaryEmail> secondaryEmails;


    @OneToOne(
            mappedBy = "profile",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JsonIgnoreProperties("profile")
    @ToString.Exclude
    private IdDocument idDocument;

    @OneToOne(
            mappedBy = "profile",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JsonIgnoreProperties("profile")
    @ToString.Exclude
    private BankDetails bankDetails;

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




// TODO: 2/9/2022 Add this to investor management service
//enum PrimaryOrJoint {
//    PRIMARY,JOINT
//}




