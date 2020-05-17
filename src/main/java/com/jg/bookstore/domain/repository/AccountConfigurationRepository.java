package com.jg.bookstore.domain.repository;

import com.jg.bookstore.domain.entity.AccountConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountConfigurationRepository extends JpaRepository<AccountConfiguration, UUID> {
}
