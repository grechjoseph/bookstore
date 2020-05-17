package com.jg.bookstore.api.controller;

import com.jg.bookstore.api.model.ApiUserRegistration;
import com.jg.bookstore.domain.entity.AccountConfiguration;
import com.jg.bookstore.domain.entity.AccountDetail;
import com.jg.bookstore.domain.entity.Address;
import com.jg.bookstore.domain.entity.UserDetail;
import com.jg.bookstore.mapper.ModelMapper;
import com.jg.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper mapper;

    @PostMapping("/register")
    public void register(@RequestBody @Valid final ApiUserRegistration userRegistration) {
        final Address[] addresses = new Address[userRegistration.getAddresses().size()];
        mapper.mapAsList(userRegistration.getAddresses(), Address.class).toArray(addresses);
        userService.registerUser(mapper.map(userRegistration.getAccountDetails(), AccountDetail.class),
                mapper.map(userRegistration.getUserDetails(), UserDetail.class),
                mapper.map(userRegistration.getAccountConfiguration(), AccountConfiguration.class),
                addresses);
    }
}
