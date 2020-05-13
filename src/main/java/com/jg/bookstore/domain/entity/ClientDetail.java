package com.jg.bookstore.domain.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
@Entity
public class ClientDetail {

    @Id
    private UUID id;

    @NotEmpty
    private String clientId;

    @NotEmpty
    private String secret;

}
