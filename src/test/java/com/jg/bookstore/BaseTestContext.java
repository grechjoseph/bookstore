package com.jg.bookstore;

import com.jg.bookstore.domain.repository.*;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static com.jg.bookstore.utils.TestUtils.*;
import static org.springframework.http.HttpMethod.POST;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseTestContext {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientDetailRepository clientDetailRepository;

    @Autowired
    private AccountDetailRepository accountDetailRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    final public void beforeEach() {
        TestUtils.reset();
        CLIENT_DETAIL.setSecret(passwordEncoder.encode(CLIENT_DETAIL.getSecret()));
        ACCOUNT_DETAIL.setPassword(passwordEncoder.encode(ACCOUNT_DETAIL.getPassword()));
        clientDetailRepository.save(CLIENT_DETAIL);
        permissionRepository.save(PERMISSION);
        accountDetailRepository.save(ACCOUNT_DETAIL);
    }

    @AfterEach
    public void afterEach() {
        orderRepository.deleteAll();
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        accountDetailRepository.deleteAll();
        permissionRepository.deleteAll();
        clientDetailRepository.deleteAll();
    }

    protected <T> T doAuthorizedRequest(final HttpMethod httpMethod,
                                        final String endpoint,
                                        final Object requestBody,
                                        final Class<T> returnType) {

        final HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + obtainAccessToken(ACCOUNT_EMAIL, ACCOUNT_PASSWORD));

        return testRestTemplate.exchange("http://localhost:" + port + endpoint,
                httpMethod,
                new HttpEntity<>(requestBody, headers),
                returnType).getBody();
    }

    protected <T> T doUnauthorizedRequest(final HttpMethod httpMethod,
                                        final String endpoint,
                                        final Object requestBody,
                                        final Class<T> returnType) {
        return testRestTemplate.exchange("http://localhost:" + port + endpoint,
                httpMethod,
                new HttpEntity<>(requestBody, null),
                returnType).getBody();
    }

    public String obtainAccessToken(final String username, final String password) {
        final Map resultMap = testRestTemplate.withBasicAuth(CLIENT_ID, CLIENT_SECRET)
                .exchange("http://localhost:" + port + "/oauth/token" +
                                "?grant_type=password&username=" + username + "&password=" + password,
                        POST,
                        new HttpEntity<>(null, null),
                        Map.class).getBody();

        return resultMap != null ? resultMap.get("access_token").toString() : null;
    }

}
