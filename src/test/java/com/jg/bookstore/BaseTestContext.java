package com.jg.bookstore;

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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Objects;

import static com.jg.bookstore.utils.TestUtils.CONTEXT;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseTestContext {

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
    final public void beforeEach() {
        TestUtils.reset();
    }

    @AfterEach
    public void afterEach() {
        orderRepository.deleteAll();
        bookRepository.deleteAll();
        authorRepository.deleteAll();
    }

    protected   <T> T doRequest(final HttpMethod httpMethod,
                                final String endpoint,
                                final Object requestBody,
                                final Class<T> returnType) {

        final MultiValueMap<String, String> headers = new HttpHeaders();

        if(Objects.nonNull(CONTEXT.getDisplayCurrency())) {
            headers.put("display-currency", List.of(CONTEXT.getDisplayCurrency().toString()));
        }

        return testRestTemplate.exchange("http://localhost:" + port + endpoint,
                httpMethod,
                new HttpEntity<>(requestBody, headers),
                returnType,
                new Object()).getBody();
    }

}
