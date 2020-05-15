package com.jg.bookstore.domain.repository;

import com.jg.bookstore.domain.entity.AccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountDetailRepository extends JpaRepository<AccountDetail, UUID> {

    Optional<AccountDetail> findByEmail(String username);

}
