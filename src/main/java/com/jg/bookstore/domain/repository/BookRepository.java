package com.jg.bookstore.domain.repository;

import com.jg.bookstore.domain.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    List<Book> findByAuthorIdAndDeletedFalse(final UUID authorId);

    /**
     * Find a Book by ID and lock it for updating (returns deleted ones as well).
     * @param id The Book's ID.
     * @return The Book object retrieved.
     */
    @Query(value = "SELECT * from bookstore.book b WHERE b.id=?1 FOR UPDATE", nativeQuery = true)
    Optional<Book> findByIdAndLock(final UUID id);

    Optional<Book> findByIdAndDeletedFalse(final UUID bookId);

    List<Book> findByDeletedFalse();

}
