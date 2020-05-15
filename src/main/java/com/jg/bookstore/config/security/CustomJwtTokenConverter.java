package com.jg.bookstore.config.security;

import com.jg.bookstore.domain.entity.AccountDetail;
import com.jg.bookstore.domain.entity.Permission;
import com.jg.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Enhances a JWT Token being issued with custom properties.
 */
@RequiredArgsConstructor
public class CustomJwtTokenConverter extends JwtAccessTokenConverter {

    public static final String USERNAME = "username";
    public static final String USER_ID = "user_id";
    public static final String JTI = "jti";
    public static final String AUTHORITY = "authorities";
    public static final String DISPLAY_CURRENCY = "displayCurrency";

    private final UserService userService;

    @Override
    public OAuth2AccessToken enhance(final OAuth2AccessToken accessToken, final OAuth2Authentication authentication) {
        final AccountDetail accountDetail = (AccountDetail) userService.loadUserByUsername(authentication.getName());

        final Map<String, Object> additionalInfo = new HashMap<>();

        additionalInfo.put(USER_ID, accountDetail.getId());
        additionalInfo.put(USERNAME, accountDetail.getEmail());
        additionalInfo.put(JTI, UUID.randomUUID().toString());
        additionalInfo.put(DISPLAY_CURRENCY, accountDetail.getAccountConfiguration().getDisplayCurrency());
        final List<String> permissions = accountDetail.getPermissions().stream().map(Permission::getName).collect(Collectors.toList());
        additionalInfo.put(AUTHORITY, permissions);

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

        final String encoded = super.encode(accessToken, authentication);
        ((DefaultOAuth2AccessToken) accessToken).setValue(encoded);

        return super.enhance(accessToken, authentication);
    }

    @Override
    public OAuth2Authentication extractAuthentication(final Map<String, ?> claims) {
        OAuth2Authentication authentication = super.extractAuthentication(claims);
        authentication.setDetails(claims);
        return authentication;
    }

}
