package com.jg.bookstore.domain.repository;

import com.jg.bookstore.domain.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<PurchaseOrder, UUID> {
}
