package com.jg.bookstore.domain.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
public class UserDetail {

    @Id
    private UUID id = UUID.randomUUID();

    @NotNull
    @OneToOne
    @JoinColumn(name = "account_id")
    private AccountDetail accountDetail;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String mobileNumber;

    @OneToMany(mappedBy = "userDetail")
    private Set<Address> addresses;

}
