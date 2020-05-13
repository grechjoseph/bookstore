package com.jg.bookstore.domain.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
public class UserDetail {

    @Id
    private UUID id;

    @NotNull
    @OneToOne
    private AccountDetail account;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String mobileNumber;

    @OneToMany(mappedBy = "userDetail")
    private Set<Address> addresses;

}
