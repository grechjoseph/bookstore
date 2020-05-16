package com.jg.bookstore.api.controller;

import com.jg.bookstore.BaseTestContext;
import com.jg.bookstore.api.model.ApiBook;
import com.jg.bookstore.domain.repository.AuthorRepository;
import com.jg.bookstore.domain.repository.BookRepository;
import com.jg.bookstore.service.ForexService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Currency;
import java.util.UUID;

import static com.jg.bookstore.utils.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.*;

public class BookControllerIT extends BaseTestContext {

    @Autowired
    private ForexService forexService;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void before() {
        authorRepository.save(AUTHOR);
        bookRepository.save(BOOK);
        // TODO CONTEXT.setDisplayCurrency(Currency.getInstance("GBP"));
        // TODO API_BOOK.setConvertedPrice(forexService.convert(API_BOOK.getPrice(), CONTEXT.getDisplayCurrency()));
    }

    @Test
    public void getBookById_shouldGetBook() {
        final ApiBook result = doRequest(GET, "/books/" + BOOK_ID, null, ApiBook.class);
        assertThat(result).isEqualTo(API_BOOK);
    }

    @Test
    public void getBooks_shouldReturnBooks() {
        final ApiBook[] expected = new ApiBook[] { API_BOOK };
        final ApiBook[] result = doRequest(GET, "/books", null, ApiBook[].class);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void updateBook_shouldUpdateAndReturnBook() {
        bookRepository.save(BOOK);
        final String newName = UUID.randomUUID().toString();
        API_BOOK.setName(newName);
        final ApiBook result = doRequest(PUT, "/books/" + BOOK_ID, API_BOOK, ApiBook.class);
        assertThat(result).isEqualTo(API_BOOK);
        assertThat(bookRepository.findById(BOOK_ID).isPresent()).isTrue();
    }

    @Test
    public void deleteBook_shouldSoftDeleteBook() {
        assertThat(bookRepository.findById(BOOK_ID).get().isDeleted()).isFalse();
        doRequest(DELETE, "books/" + BOOK_ID, null, null);
        assertThat(bookRepository.findById(BOOK_ID).get().isDeleted()).isTrue();
    }
}
