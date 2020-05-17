package com.jg.bookstore.domain.entity;

import com.jg.bookstore.domain.enums.AddressType;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import static javax.persistence.EnumType.STRING;

@Data
@Entity
public class Address {

    @Id
    private UUID id = UUID.randomUUID();

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDetail userDetail;

    @NotNull
    @Enumerated(STRING)
    private AddressType addressType;

    @NotEmpty
    private String addressLine1;

    @NotEmpty
    private String addressLine2;

    @NotEmpty
    private String city;

    @NotEmpty
    private String country;

    @NotEmpty
    private String postCode;

}
