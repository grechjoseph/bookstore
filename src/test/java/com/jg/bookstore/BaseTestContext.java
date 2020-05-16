package com.jg.bookstore;

import com.jg.bookstore.domain.entity.AccountDetail;
import com.jg.bookstore.domain.entity.ClientDetail;
import com.jg.bookstore.domain.entity.Permission;
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
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.jg.bookstore.domain.enums.AccountStatus.ACTIVE;
import static org.springframework.http.HttpMethod.POST;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseTestContext {

    public static final String CLIENT_ID = "client";
    public static final String CLIENT_SECRET = "secret";
    public static final String ACCOUNT_EMAIL = "admin@mail.com";
    public static final String ACCOUNT_PASSWORD = "123456";

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
        // TODO These entities should move to TestUtils, and should be persisted only in test suites that use/require them, just like AUTHOR, BOOK, etc...
        clientDetailRepository.save(sampleClientDetail());
        final AccountDetail accountDetail = sampleAccountDetail();
        accountDetail.setPermissions(Set.of(permissionRepository.save(sampleAdminPermission())));
        accountDetailRepository.save(accountDetail);
        TestUtils.reset();
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

    protected <T> T doRequest(final HttpMethod httpMethod,
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

    public String obtainAccessToken(final String username, final String password) {
        final Map resultMap = testRestTemplate.withBasicAuth(CLIENT_ID, CLIENT_SECRET)
                .exchange("http://localhost:" + port + "/oauth/token" +
                                "?grant_type=password&username=" + username + "&password=" + password,
                        POST,
                        new HttpEntity<>(null, null),
                        Map.class).getBody();

        return resultMap != null ? resultMap.get("access_token").toString() : null;
    }

    private ClientDetail sampleClientDetail() {
        final ClientDetail sampleClientDetail = new ClientDetail();
        sampleClientDetail.setClientId(CLIENT_ID);
        sampleClientDetail.setSecret(passwordEncoder.encode(CLIENT_SECRET));
        return sampleClientDetail;
    }

    private AccountDetail sampleAccountDetail() {
        final AccountDetail accountDetail = new AccountDetail();
        accountDetail.setEmail(ACCOUNT_EMAIL);
        accountDetail.setPassword(passwordEncoder.encode(ACCOUNT_PASSWORD));
        accountDetail.setStatus(ACTIVE);
        return accountDetail;
    }

    private Permission sampleAdminPermission() {
        final Permission permission = new Permission();
        permission.setName("ADMIN");
        permission.setDescription("Admin permission");
        return permission;
    }

}
