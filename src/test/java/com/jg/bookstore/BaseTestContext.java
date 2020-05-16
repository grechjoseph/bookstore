package com.jg.bookstore;

import com.jg.bookstore.domain.repository.AuthorRepository;
import com.jg.bookstore.domain.repository.BookRepository;
import com.jg.bookstore.domain.repository.OrderRepository;
import com.jg.bookstore.utils.TestUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.common.util.JacksonJsonParser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Objects;

import static com.jg.bookstore.utils.TestUtils.CONTEXT;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    private MockMvc mockMvc;

    @BeforeEach
    final public void beforeEach() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .addFilter(springSecurityFilterChain).build();
        TestUtils.reset();
    }

    @AfterEach
    public void afterEach() {
        orderRepository.deleteAll();
        bookRepository.deleteAll();
        authorRepository.deleteAll();
    }

    protected <T> T doRequest(final HttpMethod httpMethod,
                                final String endpoint,
                                final Object requestBody,
                                final Class<T> returnType) {

        final MultiValueMap<String, String> headers = new HttpHeaders();
        headers.put("Authorization", List.of("Bearer " + obtainAccessToken("user", "password")));

        if(Objects.nonNull(CONTEXT.getDisplayCurrency())) {
            headers.put("display-currency", List.of(CONTEXT.getDisplayCurrency().toString()));
        }

        return testRestTemplate.exchange("http://localhost:" + port + endpoint,
                httpMethod,
                new HttpEntity<>(requestBody, headers),
                returnType,
                new Object()).getBody();
    }

    @SneakyThrows
    private String obtainAccessToken(String username, String password) {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", "client");
        params.add("username", username);
        params.add("password", password);

        ResultActions result
                = mockMvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic("client","secret"))
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

}
