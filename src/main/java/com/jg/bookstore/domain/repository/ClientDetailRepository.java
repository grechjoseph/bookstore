package com.jg.bookstore.domain.repository;

import com.jg.bookstore.domain.entity.ClientDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientDetailRepository extends JpaRepository<ClientDetail, UUID> {
}
