package com.jg.bookstore.api.controller;

import com.jg.bookstore.domain.repository.AuthorRepository;
import com.jg.bookstore.domain.repository.BookRepository;
import com.jg.bookstore.domain.repository.OrderRepository;
import com.jg.bookstore.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseControllerTest {

    @LocalServerPort
    protected int port;

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    private void beforeEach() {
        TestUtils.reset();
    }

    @AfterEach
    public void afterEach() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        orderRepository.deleteAll();
    }

    protected   <T> T doRequest(final HttpMethod httpMethod,
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