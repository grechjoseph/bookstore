package com.jg.bookstore.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PermissionGroup extends JpaRepository<PermissionGroup, UUID> {
}
