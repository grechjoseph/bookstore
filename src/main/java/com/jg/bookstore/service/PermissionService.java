package com.jg.bookstore.service;

import com.jg.bookstore.domain.entity.Permission;

import java.util.List;
import java.util.UUID;

public interface PermissionService {

    Permission createPermission(final Permission permission);

    Permission getPermissionById(final UUID id);

    List<Permission> getPermissions();

    Permission updatePermission(final UUID id, final Permission newValues);

    void deletePermission(final UUID id);
}
