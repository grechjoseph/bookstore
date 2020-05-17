package com.jg.bookstore.domain.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
public class PermissionGroup {

    @Id
    private UUID id = UUID.randomUUID();

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @ManyToMany
    @JoinTable(name = "permission_group_permissions",
            joinColumns = @JoinColumn(name = "permission_group_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permission> permissions;

}
