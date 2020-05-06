package com.jg.bookstore.api.controller;

import com.jg.bookstore.api.dto.author.ApiAuthor;
import com.jg.bookstore.api.dto.author.ApiAuthorExtended;
import com.jg.bookstore.domain.repository.AuthorRepository;
import com.jg.bookstore.mapper.ModelMapper;
import com.jg.bookstore.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.Map;

import static com.jg.bookstore.utils.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthorControllerIT {

    public static final String VALIDATION_MESSAGE_AUTHOR_FIRST_NAME = "First Name must be between 2 and 20 characters.";
    public static final String VALIDATION_MESSAGE_AUTHOR_LAST_NAME = "Last Name must be between 2 and 20 characters.";
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ModelMapper mapper;

    @BeforeEach
    public void beforeEach() {
        TestUtils.reset();
    }

    @AfterEach
    public void afterEach() {
        authorRepository.deleteAll();
    }

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
    public void createAuthor_shortFirstName_shouldReturnFirstNameError() {
        final Map<String, Object> result = doRequest(POST,"/authors", new ApiAuthor("a", AUTHOR_LAST_NAME), Map.class);
        assertThat(((List<String>)result.get("errors"))).contains(VALIDATION_MESSAGE_AUTHOR_FIRST_NAME);
    }

    @Test
    public void createAuthor_longFirstName_shouldReturnFirstNameError() {
        final Map<String, Object> result = doRequest(POST,"/authors", new ApiAuthor("This is a very long first name.", AUTHOR_LAST_NAME), Map.class);
        assertThat(((List<String>)result.get("errors"))).contains(VALIDATION_MESSAGE_AUTHOR_FIRST_NAME);
    }

    @Test
    public void createAuthor_nullFirstName_shouldReturnFirstNameError() {
        final Map<String, Object> result = doRequest(POST,"/authors", new ApiAuthor(null, AUTHOR_LAST_NAME), Map.class);
        assertThat(((List<String>)result.get("errors"))).contains(VALIDATION_MESSAGE_AUTHOR_FIRST_NAME);
    }

    @Test
    public void createAuthor_shortLastName_shouldReturnLastNameError() {
        final Map<String, Object> result = doRequest(POST,"/authors", new ApiAuthor(AUTHOR_FIRST_NAME, "a"), Map.class);
        assertThat(((List<String>)result.get("errors"))).contains(VALIDATION_MESSAGE_AUTHOR_LAST_NAME);
    }

    @Test
    public void createAuthor_longLastName_shouldReturnLastNameError() {
        final Map<String, Object> result = doRequest(POST,"/authors", new ApiAuthor(AUTHOR_FIRST_NAME, "This is a very long last name."), Map.class);
        assertThat(((List<String>)result.get("errors"))).contains(VALIDATION_MESSAGE_AUTHOR_LAST_NAME);
    }

    @Test
    public void createAuthor_nullLastName_shouldReturnLastNameError() {
        final Map<String, Object> result = doRequest(POST,"/authors", new ApiAuthor(AUTHOR_FIRST_NAME, null), Map.class);
        assertThat(((List<String>)result.get("errors"))).contains(VALIDATION_MESSAGE_AUTHOR_LAST_NAME);
    }

    @Test
    public void createAuthor_nullFirstNameAndLastName_shouldReturnFirstNameAndLastNameError() {
        final Map<String, Object> result = doRequest(POST,"/authors", new ApiAuthor(null, null), Map.class);
        assertThat(((List<String>)result.get("errors"))).contains(VALIDATION_MESSAGE_AUTHOR_FIRST_NAME);
        assertThat(((List<String>)result.get("errors"))).contains(VALIDATION_MESSAGE_AUTHOR_LAST_NAME);
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
    public void updateAuthor_shouldUpdateAuthor() {
        authorRepository.save(AUTHOR);
        final ApiAuthor expected = new ApiAuthor("New First Name", "New Last Name");
        final ApiAuthorExtended result = doRequest(PUT, "/authors/" + AUTHOR_ID, expected, ApiAuthorExtended.class);
        assertThat(result).isEqualTo(expected);
        assertThat(mapper.map(authorRepository.findById(AUTHOR_ID).get(), ApiAuthorExtended.class)).isEqualTo(result);
    }

    @Test
    public void deleteAuthor_shouldSoftDeleteAuthor() {
        authorRepository.save(AUTHOR);
        doRequest(DELETE, "/authors/" + AUTHOR_ID, null, null);
        assertThat(authorRepository.findById(AUTHOR_ID).get().isDeleted()).isTrue();
    }

    private <T> T doRequest(final HttpMethod httpMethod,
                        final String endpoint,
                        final Object requestBody,
                        final Class<T> returnType) {
        return testRestTemplate.exchange("http://localhost:" + port + endpoint,
                httpMethod,
                new HttpEntity<>(requestBody),
                returnType,
                new Object()).getBody();
    }

}
