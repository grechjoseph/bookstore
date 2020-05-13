package com.jg.bookstore.domain.entity;

import com.jg.bookstore.domain.enums.AccountStatus;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static javax.persistence.EnumType.STRING;

@Data
@Entity
public class AccountDetail {

    @Id
    private UUID id;

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotNull
    @Enumerated(STRING)
    private AccountStatus status;

    @ManyToMany
    @JoinTable(name = "account_permissions",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permission> permissions;

    @ManyToMany
    @JoinTable(name = "account_permission_groups",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_group_id"))
    private Set<PermissionGroup> permissionGroups;

    public Set<Permission> getPermissions() {
        final Set<Permission> permissions = new HashSet<>(this.permissions);
        permissionGroups.forEach(permissionGroup -> permissions.addAll(permissionGroup.getPermissions()));
        return permissions;
    }

}
