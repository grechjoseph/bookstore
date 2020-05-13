package com.jg.bookstore.domain.repository;

import com.jg.bookstore.domain.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, UUID> {
}
