package com.jg.bookstore.service;

import com.jg.bookstore.domain.entity.PermissionGroup;

import java.util.List;
import java.util.UUID;

public interface PermissionGroupService {

    PermissionGroup createPermissionGroup(final PermissionGroup permissionGroup);

    PermissionGroup getPermmissionGroupById(final UUID id);

    List<PermissionGroup> getPermissionGroups();

    PermissionGroup updatePermissionGroup(final UUID id, final PermissionGroup permissionGroup);

    void deletePermissionGroup(final UUID id);
}
