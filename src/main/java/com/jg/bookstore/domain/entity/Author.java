package com.jg.bookstore.domain.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
@Entity
public class Author {

    @Id
    private UUID id = UUID.randomUUID();

    private boolean deleted;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

}
