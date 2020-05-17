package com.jg.bookstore.api.controller;

import com.jg.bookstore.BaseTestContext;
import com.jg.bookstore.domain.repository.AuthorRepository;
import com.jg.bookstore.domain.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.jg.bookstore.utils.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.PUT;

public class BookControllerTest extends BaseTestContext {

    private static final String VALIDATION_MESSAGE_BOOK_NAME = "Book's Name has to be between 1 and 100 characters.";
    private static final String VALIDATION_MESSAGE_BOOK_STOCK = "Book's Stock has to be positive.";
    private static final String VALIDATION_MESSAGE_BOOK_PRICE = "Book's Price has to be positive.";

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void before() {
        authorRepository.save(AUTHOR);
        bookRepository.save(BOOK);
    }

    @Test
    public void updateBook_nullName_shouldReturnNameError() {
        API_BOOK.setName(null);
        Map<String, Object> result = doAuthorizedRequest(PUT, "/books/" + BOOK_ID, API_BOOK, Map.class);
        assertThat(((List<String>)result.get("errors"))).contains(VALIDATION_MESSAGE_BOOK_NAME);
    }

    @Test
    public void updateBook_negativeStock_shouldReturnStockError() {
        API_BOOK.setStock(-1);
        Map<String, Object> result = doAuthorizedRequest(PUT, "/books/" + BOOK_ID, API_BOOK, Map.class);
        assertThat(((List<String>)result.get("errors"))).contains(VALIDATION_MESSAGE_BOOK_STOCK);
    }

    @Test
    public void updateBook_negativePrice_shouldReturnPriceError() {
        API_BOOK.setPrice(BigDecimal.valueOf(-1.00));
        Map<String, Object> result = doAuthorizedRequest(PUT, "/books/" + BOOK_ID, API_BOOK, Map.class);
        assertThat(((List<String>)result.get("errors"))).contains(VALIDATION_MESSAGE_BOOK_PRICE);
    }

}
