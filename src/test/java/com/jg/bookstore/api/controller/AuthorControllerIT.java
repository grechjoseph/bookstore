package com.jg.bookstore.api.controller;

import com.jg.bookstore.BaseTestContext;
import com.jg.bookstore.api.model.ApiAuthor;
import com.jg.bookstore.api.model.ApiBook;
import com.jg.bookstore.domain.repository.AuthorRepository;
import com.jg.bookstore.domain.repository.BookRepository;
import com.jg.bookstore.mapper.ModelMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.jg.bookstore.utils.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.*;

public class AuthorControllerIT extends BaseTestContext {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper mapper;

    @Test
    public void createAuthor_validModel_shouldCreateAuthor() {
        final ApiAuthor result = doRequest(POST,"/authors", API_AUTHOR, ApiAuthor.class);
        assertThat(result.getFirstName()).isEqualTo(API_AUTHOR.getFirstName());
        assertThat(result.getLastName()).isEqualTo(API_AUTHOR.getLastName());
        assertThat(result.getId()).isNotNull();
        final ApiAuthor dbObject = mapper.map(authorRepository.findById(result.getId()).get(), ApiAuthor.class);
        assertThat(dbObject).isEqualTo(result);
    }

    @Test
    public void getAuthorById_shouldRetrieveAuthor() {
        authorRepository.save(AUTHOR);
        final ApiAuthor result = doRequest(GET, "/authors/" + AUTHOR_ID, null, ApiAuthor.class);
        assertThat(result).isEqualTo(API_AUTHOR);
    }

    @Test
    public void getAuthors_shouldRetrieveAuthorsList() {
        authorRepository.save(AUTHOR);
        final ApiAuthor[] expected = new ApiAuthor[] { API_AUTHOR };
        ApiAuthor[] result = doRequest(GET, "/authors", null, ApiAuthor[].class);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void updateAuthor_shouldUpdateAndReturnAuthor() throws Exception {
        authorRepository.save(AUTHOR);
        API_AUTHOR.setFirstName("New First Name");
        API_AUTHOR.setLastName("New Last Name");
        final ApiAuthor result = doRequest(PUT, "/authors/" + AUTHOR_ID, API_AUTHOR, ApiAuthor.class);
        assertThat(result).isEqualTo(API_AUTHOR);
        assertThat(mapper.map(authorRepository.findById(AUTHOR_ID).orElseThrow(Exception::new), ApiAuthor.class)).isEqualTo(result);
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
        final ApiBook[] expected = new ApiBook[] { API_BOOK };
        final ApiBook[] result = doRequest(GET, "/authors/" + AUTHOR_ID + "/books", null, ApiBook[].class);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void createAuthorBook_shouldCreateBook() {
        authorRepository.save(AUTHOR);
        final ApiBook result = doRequest(POST, "/authors/" + AUTHOR_ID + "/books", API_BOOK, ApiBook.class);
        assertThat(result).isEqualTo(API_BOOK);
        assertThat(mapper.mapAsList(bookRepository.findByAuthorIdAndDeletedFalse(AUTHOR_ID), ApiBook.class))
                .isEqualTo(List.of(API_BOOK));
    }

}
