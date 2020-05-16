package com.jg.bookstore.config.security;

import com.jg.bookstore.domain.entity.AccountDetail;
import com.jg.bookstore.domain.entity.Permission;
import com.jg.bookstore.domain.exception.BaseException;
import com.jg.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;
import java.util.stream.Collectors;

import static com.jg.bookstore.domain.exception.ErrorCode.INVALID_JWT_TOKEN;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Locale.ROOT;
import static org.apache.logging.log4j.util.Strings.EMPTY;

/**
 * Enhances a JWT Token being issued with custom properties.
 */
@Slf4j
@RequiredArgsConstructor
public class CustomJwtTokenConverter extends JwtAccessTokenConverter {

    public static final String USERNAME = "username";
    public static final String USER_ID = "user_id";
    public static final String JTI = "jti";
    public static final String AUTHORITY = "authorities";
    public static final String DISPLAY_CURRENCY = "displayCurrency";
    public static final String HASH = "hash";

    private final UserService userService;

    @Value(value = "${jwt.hash.secret:secret}")
    private String hashSecret;

    @Override
    public OAuth2AccessToken enhance(final OAuth2AccessToken accessToken, final OAuth2Authentication authentication) {
        final AccountDetail accountDetail = (AccountDetail) userService.loadUserByUsername(authentication.getName());

        final Map<String, Object> additionalInfo = new HashMap<>();

        additionalInfo.put(USER_ID, accountDetail.getId());
        additionalInfo.put(USERNAME, accountDetail.getEmail());
        additionalInfo.put(DISPLAY_CURRENCY, accountDetail.getAccountConfiguration().getDisplayCurrency());
        final List<String> permissions = accountDetail.getPermissions().stream().map(Permission::getName).collect(Collectors.toList());
        additionalInfo.put(AUTHORITY, permissions);
        additionalInfo.put(JTI, UUID.randomUUID().toString());
        log.debug("Hashing Jwt payload.");
        additionalInfo.put(HASH, hash(additionalInfo.get(USER_ID).toString(),
                additionalInfo.get(USERNAME).toString(),
                additionalInfo.get(DISPLAY_CURRENCY).toString(),
                additionalInfo.get(AUTHORITY).toString(),
                additionalInfo.get(JTI).toString(),
                hashSecret));

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

        final String encoded = super.encode(accessToken, authentication);
        ((DefaultOAuth2AccessToken) accessToken).setValue(encoded);

        return super.enhance(accessToken, authentication);
    }

    @Override
    public OAuth2Authentication extractAuthentication(final Map<String, ?> claims) {
        final String expectedHash = hash(claims.get(USER_ID).toString(),
                claims.get(USERNAME).toString(),
                claims.get(DISPLAY_CURRENCY).toString(),
                claims.get(AUTHORITY).toString(),
                claims.get(JTI).toString(),
                hashSecret);
        log.debug("Evaluating Jwt Hash: {}.", claims.get(HASH));

        if(!expectedHash.equals(claims.get(HASH))) {
            log.error("Failed to evaluate hash {}!", claims.get(HASH));
            log.debug("Payload: {}", claims);
            throw new BaseException(INVALID_JWT_TOKEN);
        }

        OAuth2Authentication authentication = super.extractAuthentication(claims);
        authentication.setDetails(claims);
        return authentication;
    }

    @SneakyThrows
    private String hash(String... strings) {
        return String.format(ROOT,
                "%032x",
                new BigInteger(1, MessageDigest.getInstance("SHA-256").digest(
                        String.join(EMPTY, strings).getBytes(UTF_8))));
    }

}
