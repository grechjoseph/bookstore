package com.jg.bookstore.api.controller;

import com.jg.bookstore.api.model.author.ApiAuthor;
import com.jg.bookstore.api.model.author.ApiAuthorExtended;
import com.jg.bookstore.api.model.book.ApiBook;
import com.jg.bookstore.api.model.book.ApiBookExtended;
import com.jg.bookstore.domain.repository.AuthorRepository;
import com.jg.bookstore.domain.repository.BookRepository;
import com.jg.bookstore.mapper.ModelMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.jg.bookstore.utils.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.*;

public class AuthorControllerIT extends BaseControllerTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper mapper;

    @Test
    public void createAuthor_validModel_shouldCreateAuthor() {
        final ApiAuthorExtended result = doRequest(POST,"/authors", API_AUTHOR, ApiAuthorExtended.class);
        assertThat(result.getFirstName()).isEqualTo(API_AUTHOR.getFirstName());
        assertThat(result.getLastName()).isEqualTo(API_AUTHOR.getLastName());
        assertThat(result.getId()).isNotNull();
        final ApiAuthorExtended dbObject = mapper.map(authorRepository.findById(result.getId()).get(), ApiAuthorExtended.class);
        assertThat(dbObject).isEqualTo(result);
    }

    @Test
    public void getAuthorById_shouldRetrieveAuthor() {
        authorRepository.save(AUTHOR);
        final ApiAuthorExtended result = doRequest(GET, "/authors/" + AUTHOR_ID, null, ApiAuthorExtended.class);
        assertThat(result).isEqualTo(mapper.map(AUTHOR, ApiAuthorExtended.class));
    }

    @Test
    public void getAuthors_shouldRetrieveAuthorsList() {
        authorRepository.save(AUTHOR);
        final ApiAuthorExtended[] expected = new ApiAuthorExtended[] { mapper.map(AUTHOR, ApiAuthorExtended.class) };
        ApiAuthorExtended[] result = doRequest(GET, "/authors", null, ApiAuthorExtended[].class);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void updateAuthor_shouldUpdateAuthor() throws Exception {
        authorRepository.save(AUTHOR);
        final ApiAuthor expected = new ApiAuthor("New First Name", "New Last Name");
        final ApiAuthorExtended result = doRequest(PUT, "/authors/" + AUTHOR_ID, expected, ApiAuthorExtended.class);
        assertThat(result).isEqualTo(expected);
        assertThat(mapper.map(authorRepository.findById(AUTHOR_ID).orElseThrow(Exception::new), ApiAuthorExtended.class)).isEqualTo(result);
    }

    @Test
    public void deleteAuthor_shouldSoftDeleteAuthor() throws Exception {
        authorRepository.save(AUTHOR);
        doRequest(DELETE, "/authors/" + AUTHOR_ID, null, null);
        assertThat(authorRepository.findById(AUTHOR_ID).orElseThrow(Exception::new).isDeleted()).isTrue();
    }

    @Test
    public void getAuthorBooks_shouldReturnBooks() {
        authorRepository.save(AUTHOR);
        bookRepository.save(BOOK);
        final ApiBook[] expected = new ApiBook[] { mapper.map(BOOK, ApiBook.class) };
        final ApiBook[] result = doRequest(GET, "/authors/" + AUTHOR_ID + "/books", null, ApiBook[].class);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void createAuthorBook_shouldCreateBook() {
        authorRepository.save(AUTHOR);
        final ApiBookExtended result = doRequest(POST, "/authors/" + AUTHOR_ID + "/books", mapper.map(BOOK, ApiBook.class), ApiBookExtended.class);
        assertThat(result).isEqualTo(mapper.map(BOOK, ApiBookExtended.class));
        assertThat(mapper.mapAsList(bookRepository.findByAuthorIdAndDeletedFalse(AUTHOR_ID), ApiBook.class))
                .isEqualTo(List.of(mapper.map(BOOK, ApiBook.class)));
    }

}
