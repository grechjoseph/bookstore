package com.jg.bookstore.api.controller;

import com.jg.bookstore.api.model.ApiUserRegistration;
import com.jg.bookstore.domain.entity.AccountConfiguration;
import com.jg.bookstore.domain.entity.AccountDetail;
import com.jg.bookstore.domain.entity.Address;
import com.jg.bookstore.domain.entity.UserDetail;
import com.jg.bookstore.mapper.ModelMapper;
import com.jg.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapEntry;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper mapper;

    @PostMapping("/register")
    @PreAuthorize("hasRole('REGISTER_ACCOUNT')")
    public MapEntry<String, String> register(@RequestBody @Valid final ApiUserRegistration userRegistration) {
        final Address[] addresses = new Address[userRegistration.getAddresses().size()];
        mapper.mapAsList(userRegistration.getAddresses(), Address.class).toArray(addresses);
        return new MapEntry<>("accountId", userService.registerUser(mapper.map(userRegistration.getAccountDetails(), AccountDetail.class),
                mapper.map(userRegistration.getUserDetails(), UserDetail.class),
                mapper.map(userRegistration.getAccountConfiguration(), AccountConfiguration.class),
                addresses));
    }

    @PostMapping("/verify/{accountDetailId}")
    @PreAuthorize("hasRole('VERIFY_ACCOUNT')")
    public void verify(@PathVariable final UUID accountDetailId) {
        userService.verifyEmail(accountDetailId);
    }
}
