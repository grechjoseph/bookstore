package com.jg.bookstore.service.impl;

import com.jg.bookstore.BaseTestContext;
import com.jg.bookstore.domain.entity.AccountConfiguration;
import com.jg.bookstore.domain.entity.AccountDetail;
import com.jg.bookstore.domain.entity.Address;
import com.jg.bookstore.domain.entity.UserDetail;
import com.jg.bookstore.domain.repository.AccountConfigurationRepository;
import com.jg.bookstore.domain.repository.AccountDetailRepository;
import com.jg.bookstore.domain.repository.AddressRepository;
import com.jg.bookstore.domain.repository.UserDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.UUID;

import static com.jg.bookstore.domain.enums.AddressType.BILLING;
import static com.jg.bookstore.domain.enums.AddressType.SHIPPING;
import static org.mockito.Mockito.*;

public class UserServiceImplTest extends BaseTestContext {

    @Mock
    private AccountDetailRepository mockAccountDetailRepository;

    @Mock
    private AccountConfigurationRepository mockAccountConfigurationRepository;

    @Mock
    private UserDetailRepository mockUserDetailRepository;

    @Mock
    private AddressRepository mockAddressRepository;

    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void init() {

    }

    @Test
    public void registerUser_validModel_saveAccountObjects() {
        final AccountDetail accountDetail = mock(AccountDetail.class);
        when(accountDetail.getPassword()).thenReturn("123456");
        final UserDetail userDetail = mock(UserDetail.class);
        final AccountConfiguration accountConfiguration = mock(AccountConfiguration.class);
        final Address shippingAddress = mock(Address.class);
        final Address billingAddress = mock(Address.class);

        when(mockPasswordEncoder.encode("123456")).thenReturn("123456");
        when(shippingAddress.getAddressType()).thenReturn(SHIPPING);
        when(billingAddress.getAddressType()).thenReturn(BILLING);
        when(accountDetail.getId()).thenReturn(UUID.randomUUID());
        when(mockAccountDetailRepository.save(any())).thenReturn(accountDetail);

        userService.registerUser(accountDetail, userDetail, accountConfiguration, shippingAddress, billingAddress);

        verify(accountConfiguration).setAccountDetail(accountDetail);
        verify(userDetail).setAccountDetail(accountDetail);
        verify(shippingAddress).setUserDetail(userDetail);
        verify(billingAddress).setUserDetail(userDetail);

        verify(mockPasswordEncoder).encode(accountDetail.getPassword());
        verify(mockAccountDetailRepository).save(accountDetail);
        verify(mockAccountConfigurationRepository).save(accountConfiguration);
        verify(mockUserDetailRepository).save(userDetail);
        verify(mockAddressRepository).saveAll(Arrays.asList(shippingAddress, billingAddress));
    }

}
