package com.jg.bookstore.service;

import com.jg.bookstore.domain.entity.Author;

import java.util.List;
import java.util.UUID;

public interface AuthorService {

    Author createAuthor(final Author author);

    Author getAuthorById(final UUID authorId);

    List<Author> getAuthors();

    Author updateAuthor(final UUID authorId, final Author newValues);

    void deleteAuthor(final UUID authorId);

}
