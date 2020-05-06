package com.jg.bookstore.domain.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
public class Author {

    @Id
    private UUID id = UUID.randomUUID();

    private boolean deleted;
    private String firstName;
    private String lastName;

}
