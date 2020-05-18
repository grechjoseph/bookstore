package com.jg.bookstore.service.impl;

import com.jg.bookstore.domain.entity.AccountConfiguration;
import com.jg.bookstore.domain.entity.AccountDetail;
import com.jg.bookstore.domain.entity.Address;
import com.jg.bookstore.domain.entity.UserDetail;
import com.jg.bookstore.domain.enums.AccountStatus;
import com.jg.bookstore.domain.exception.BaseException;
import com.jg.bookstore.domain.repository.AccountConfigurationRepository;
import com.jg.bookstore.domain.repository.AccountDetailRepository;
import com.jg.bookstore.domain.repository.AddressRepository;
import com.jg.bookstore.domain.repository.UserDetailRepository;
import com.jg.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.UUID;

import static com.jg.bookstore.domain.enums.AddressType.BILLING;
import static com.jg.bookstore.domain.exception.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AccountDetailRepository accountDetailRepository;
    private final AccountConfigurationRepository accountConfigurationRepository;
    private final UserDetailRepository userDetailRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void registerUser(final AccountDetail accountDetail,
                                   final UserDetail userDetail,
                                   final AccountConfiguration accountConfiguration,
                                   final Address... addresses) {

        // Validate.
        validateAccountDetail(accountDetail);
        validateAddresses(addresses);
        validateUserDetail(userDetail);
        validateAccountConfiguration(accountConfiguration);

        // Sanitize.
        accountDetail.setPassword(passwordEncoder.encode(accountDetail.getPassword()));

        // Link.
        accountConfiguration.setAccountDetail(accountDetail);
        userDetail.setAccountDetail(accountDetail);
        Arrays.stream(addresses).forEach(address -> address.setUserDetail(userDetail));

        // Persist.
        accountDetailRepository.save(accountDetail);
        accountConfigurationRepository.save(accountConfiguration);
        userDetailRepository.save(userDetail);
        addressRepository.saveAll(Arrays.asList(addresses));

        // Notify.
    }

    @Override
    public UserDetail updatePersonalInfo(final UUID userDetailId, final UserDetail newValues) {
        return null;
    }

    @Override
    public void updatePassword(final UUID accountDetailId, final String newPassword) {

    }

    @Override
    public void updateAccountDetailStatus(final UUID accountDetailId, final AccountStatus newStatus) {

    }

    @Override
    public UserDetail updateAddresses(final UUID userDetailId, final Address... addresses) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return accountDetailRepository.findByEmail(username).orElseThrow(() -> new BaseException(ACCOUNT_NOT_FOUND));
    }

    private void validateAccountDetail(final AccountDetail candidate) {
        accountDetailRepository.findByEmail(candidate.getEmail()).ifPresent(accountDetail -> {
            if(!accountDetail.getId().equals(candidate.getId())) {
                throw new BaseException(ACCOUNT_EMAIL_NOT_AVAILABLE);
            }
        });
    }

    private void validateAddresses(final Address... addresses) {
        if(Arrays.stream(addresses).filter(address -> address.getAddressType().equals(BILLING)).count() > 1) {
            throw new BaseException(ADDRESS_MORE_THAN_ONE_BILLING);
        }
    }

    private void validateUserDetail(final UserDetail candidate) {

    }

    private void validateAccountConfiguration(final AccountConfiguration candidate) {

    }
}
