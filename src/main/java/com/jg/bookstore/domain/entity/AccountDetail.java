package com.jg.bookstore.domain.entity;

import com.jg.bookstore.domain.enums.AccountStatus;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.EAGER;

@Data
@Entity
public class AccountDetail implements UserDetails {

    @Id
    private UUID id;

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotNull
    @Enumerated(STRING)
    private AccountStatus status;

    @ManyToMany(fetch = EAGER)
    @JoinTable(name = "account_permissions",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permission> permissions;

    @ManyToMany(fetch = EAGER)
    @JoinTable(name = "account_permission_groups",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_group_id"))
    private Set<PermissionGroup> permissionGroups;

    public Set<Permission> getPermissions() {
        final Set<Permission> permissions = new HashSet<>(this.permissions);
        permissionGroups.forEach(permissionGroup -> permissions.addAll(permissionGroup.getPermissions()));
        return permissions;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<>();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
