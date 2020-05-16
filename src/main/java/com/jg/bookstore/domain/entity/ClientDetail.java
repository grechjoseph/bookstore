package com.jg.bookstore.domain.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Data
@Entity
public class ClientDetail implements ClientDetails {

    private static final String CLIENT_CREDENTIALS = "client_credentials";
    private static final String PASSWORD = "password";
    private static final String REFRESH_TOKEN = "refresh_token";

    @Id
    private UUID id = UUID.randomUUID();

    @NotEmpty
    private String clientId;

    @NotEmpty
    private String secret;

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public Set<String> getResourceIds() {
        return null;
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        return secret;
    }

    @Override
    public boolean isScoped() {
        return false;
    }

    @Override
    public Set<String> getScope() {
        return new HashSet<>(List.of("test"));
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return new HashSet<>(List.of(CLIENT_CREDENTIALS, PASSWORD, REFRESH_TOKEN));
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return null;
    }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return new HashSet<>();
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return false;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }
}
