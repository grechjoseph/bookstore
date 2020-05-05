package com.jg.bookstore.service;

import com.jg.bookstore.domain.entity.Book;

import java.util.List;
import java.util.UUID;

public interface BookService {

    Book createBook(final UUID authorId, final Book book);

    Book getBookById(final UUID bookId);

    List<Book> getBooks(final UUID authorId);

    Book updateBook(final UUID bookId, final Book newValues);

    void deleteBook(final UUID bookId);

}
