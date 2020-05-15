package com.jg.bookstore.config.security.context;

import lombok.Data;

import java.util.Currency;
import java.util.List;
import java.util.UUID;

/**
 * Holds select User properties.
 */
@Data
public class Context {

    private String username;
    private UUID userId;
    private List<String> permissions;
    private Currency displayCurrency;

    public boolean isAdmin() {
        return permissions.stream().anyMatch(permission -> permission.toLowerCase().equals("admin"));
    }

}
