package com.jg.bookstore.config.security;

import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Used to call a number of properties from the UserDetails object being Authenticated. This extension allows for further
 * checks to be made.
 */
public class CustomUserDetailsChecker extends AccountStatusUserDetailsChecker {

    @Override
    public void check(UserDetails userDetails) {
        // START - Custom Checks here:

        // END
        super.check(userDetails);
    }
}
