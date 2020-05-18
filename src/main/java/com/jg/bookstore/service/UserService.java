package com.jg.bookstore.service;

import com.jg.bookstore.domain.entity.AccountConfiguration;
import com.jg.bookstore.domain.entity.AccountDetail;
import com.jg.bookstore.domain.entity.Address;
import com.jg.bookstore.domain.entity.UserDetail;
import com.jg.bookstore.domain.enums.AccountStatus;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;
import java.util.UUID;

public interface UserService extends UserDetailsService {

    Optional<AccountDetail> findByEmail(final String email);

    void registerUser(final AccountDetail accountDetail,
                            final UserDetail userDetail,
                            final AccountConfiguration accountConfiguration,
                            final Address... addresses);

    void verifyEmail(final UUID accountDetailId);

    UserDetail updatePersonalInfo(final UUID userDetailId, final UserDetail newValues);

    void updatePassword(final UUID accountDetailId, final String newPassword);

    void updateAccountDetailStatus(final UUID accountDetailId, final AccountStatus newStatus);

    UserDetail updateAddresses(final UUID userDetailId, final Address... addresses);

}
