package com.jg.bookstore.domain.repository;

import com.jg.bookstore.domain.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {

    Optional<Author> findByIdAndDeletedFalse(final UUID authorId);

    List<Author> findByDeletedFalse();

}
