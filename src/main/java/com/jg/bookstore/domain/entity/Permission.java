package com.jg.bookstore.domain.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
@Entity
public class Permission {

    @Id
    private UUID id = UUID.randomUUID();

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

}
