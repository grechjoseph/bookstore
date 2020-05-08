package com.jg.bookstore.api.controller;

import com.jg.bookstore.api.model.book.ApiBook;
import com.jg.bookstore.api.model.book.ApiBookExtended;
import com.jg.bookstore.domain.repository.AuthorRepository;
import com.jg.bookstore.domain.repository.BookRepository;
import com.jg.bookstore.mapper.ModelMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static com.jg.bookstore.utils.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.*;

public class BookControllerIT extends BaseControllerTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper mapper;

    @BeforeEach
    public void beforeEach() {
        authorRepository.save(AUTHOR);
        bookRepository.save(BOOK);
    }

    @Test
    public void getBookById_shouldGetBook() {
        final ApiBookExtended result = doRequest(GET, "/books/" + BOOK_ID, null, ApiBookExtended.class);
        assertThat(result).isEqualTo(mapper.map(BOOK, ApiBookExtended.class));
    }

    @Test
    public void getBooks_shouldReturnBooks() {
        final ApiBookExtended[] expected = new ApiBookExtended[] { mapper.map(BOOK, ApiBookExtended.class) };
        final ApiBookExtended[] result = doRequest(GET, "/books", null, ApiBookExtended[].class);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void updateBook_shouldUpdateAndReturnBook() {
        final String newName = UUID.randomUUID().toString();
        BOOK.setName(newName);
        final ApiBookExtended result = doRequest(PUT, "/books/" + BOOK_ID, mapper.map(BOOK, ApiBook.class), ApiBookExtended.class);
        assertThat(result).isEqualTo(mapper.map(BOOK, ApiBookExtended.class));
    }

    @Test
    public void deleteBook_shouldSoftDeleteBook() {
        assertThat(bookRepository.findById(BOOK_ID).get().isDeleted()).isFalse();
        doRequest(DELETE, "books/" + BOOK_ID, null, null);
        assertThat(bookRepository.findById(BOOK_ID).get().isDeleted()).isTrue();
    }
}
