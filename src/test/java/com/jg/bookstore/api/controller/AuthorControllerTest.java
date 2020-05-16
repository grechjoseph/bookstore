package com.jg.bookstore.api.controller;

import com.jg.bookstore.BaseTestContext;
import com.jg.bookstore.api.model.ApiAuthor;
import com.jg.bookstore.domain.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.jg.bookstore.utils.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.POST;

public class AuthorControllerTest extends BaseTestContext {

    private static final String VALIDATION_MESSAGE_AUTHOR_FIRST_NAME = "First Name must be between 2 and 20 characters.";
    private static final String VALIDATION_MESSAGE_AUTHOR_LAST_NAME = "Last Name must be between 2 and 20 characters.";
    private static final String VALIDATION_MESSAGE_BOOK_NAME = "Book's Name has to be between 1 and 100 characters.";
    private static final String VALIDATION_MESSAGE_BOOK_STOCK = "Book's Stock has to be positive.";
    private static final String VALIDATION_MESSAGE_BOOK_PRICE = "Book's Price has to be positive.";

    @LocalServerPort
    private int port;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void createAuthor_shortFirstName_shouldReturnFirstNameError() {
        API_AUTHOR.setFirstName("a");
        final Map<String, Object> result = doRequest(POST,"/authors", API_AUTHOR, Map.class);
        assertThat(((List<String>)result.get("errors"))).contains(VALIDATION_MESSAGE_AUTHOR_FIRST_NAME);
    }

    @Test
    public void createAuthor_longFirstName_shouldReturnFirstNameError() {
        API_AUTHOR.setFirstName("This is a very long first name.");
        final Map<String, Object> result = doRequest(POST,"/authors", API_AUTHOR, Map.class);
        assertThat(((List<String>)result.get("errors"))).contains(VALIDATION_MESSAGE_AUTHOR_FIRST_NAME);
    }

    @Test
    public void createAuthor_nullFirstName_shouldReturnFirstNameError() {
        API_AUTHOR.setFirstName(null);
        final Map<String, Object> result = doRequest(POST,"/authors", API_AUTHOR, Map.class);
        assertThat(((List<String>)result.get("errors"))).contains(VALIDATION_MESSAGE_AUTHOR_FIRST_NAME);
    }

    @Test
    public void createAuthor_shortLastName_shouldReturnLastNameError() {
        API_AUTHOR.setLastName("a");
        final Map<String, Object> result = doRequest(POST,"/authors", API_AUTHOR, Map.class);
        assertThat(((List<String>)result.get("errors"))).contains(VALIDATION_MESSAGE_AUTHOR_LAST_NAME);
    }

    @Test
    public void createAuthor_longLastName_shouldReturnLastNameError() {
        API_AUTHOR.setLastName("This is a very long last name.");
        final Map<String, Object> result = doRequest(POST,"/authors", API_AUTHOR, Map.class);
        assertThat(((List<String>)result.get("errors"))).contains(VALIDATION_MESSAGE_AUTHOR_LAST_NAME);
    }

    @Test
    public void createAuthor_nullLastName_shouldReturnLastNameError() {
        API_AUTHOR.setLastName(null);
        final Map<String, Object> result = doRequest(POST,"/authors", API_AUTHOR, Map.class);
        assertThat(((List<String>)result.get("errors"))).contains(VALIDATION_MESSAGE_AUTHOR_LAST_NAME);
    }

    @Test
    public void createAuthor_nullFirstNameAndLastName_shouldReturnFirstNameAndLastNameError() {
        final Map<String, Object> result = doRequest(POST,"/authors", new ApiAuthor(), Map.class);
        assertThat(((List<String>)result.get("errors"))).contains(VALIDATION_MESSAGE_AUTHOR_FIRST_NAME);
        assertThat(((List<String>)result.get("errors"))).contains(VALIDATION_MESSAGE_AUTHOR_LAST_NAME);
    }

    @Test
    public void createAuthorBook_nullName_shouldReturnNameError() {
        authorRepository.save(AUTHOR);
        API_BOOK.setName(null);
        Map<String, Object> result = doRequest(POST, "/authors/" + AUTHOR_ID + "/books", API_BOOK, Map.class);
        assertThat(((List<String>)result.get("errors"))).contains(VALIDATION_MESSAGE_BOOK_NAME);
    }

    @Test
    public void createAuthorBook_zeroStock_shouldReturnStockError() {
        authorRepository.save(AUTHOR);
        API_BOOK.setStock(-1);
        Map<String, Object> result = doRequest(POST, "/authors/" + AUTHOR_ID + "/books", API_BOOK, Map.class);
        assertThat(((List<String>)result.get("errors"))).contains(VALIDATION_MESSAGE_BOOK_STOCK);
    }

    @Test
    public void createAuthorBook_negativePrice_shouldReturnPriceError() {
        authorRepository.save(AUTHOR);
        API_BOOK.setPrice(BigDecimal.valueOf(-1.00));
        Map<String, Object> result = doRequest(POST, "/authors/" + AUTHOR_ID + "/books", API_BOOK, Map.class);
        assertThat(((List<String>)result.get("errors"))).contains(VALIDATION_MESSAGE_BOOK_PRICE);
    }

}
