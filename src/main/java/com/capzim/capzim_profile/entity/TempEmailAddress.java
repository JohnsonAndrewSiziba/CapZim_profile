package com.capzim.capzim_profile.entity;

import com.capzim.capzim_profile.validator.ValidMobileNumber;
import lombok.*;
import net.bytebuddy.utility.RandomString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Johnson Andrew Siziba (sizibajohnsona@gmail.com,+263784310119)
 * @version 1.0
 * Date: 6/9/2022
 * Time: 08:48
 */

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class TempEmailAddress {
    @Id
    @GeneratedValue
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;

    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID userId;

    @Email(message = "Email address is not valid")
    @Column(nullable = false)
    private String email;

    private String token = RandomString.make(9);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TempEmailAddress that = (TempEmailAddress) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
