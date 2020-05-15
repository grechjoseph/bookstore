package com.jg.bookstore.service.impl;

import com.jg.bookstore.domain.entity.ClientDetail;
import com.jg.bookstore.domain.exception.BaseException;
import com.jg.bookstore.domain.repository.ClientDetailRepository;
import com.jg.bookstore.service.ClientDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.jg.bookstore.domain.exception.ErrorCode.CLIENT_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientDetailServiceImpl implements ClientDetailService {

    private final ClientDetailRepository clientDetailRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ClientDetail createClientDetail(final ClientDetail clientDetail) {
        return null;
    }

    @Override
    public ClientDetail getClientDetailById(final UUID id) {
        return null;
    }

    @Override
    public ClientDetail updateClientDetail(final UUID id, final ClientDetail newValues) {
        return null;
    }

    @Override
    public ClientDetails loadClientByClientId(final String clientId) throws ClientRegistrationException {
        return clientDetailRepository.findByClientId(clientId).orElseThrow(() -> new BaseException(CLIENT_NOT_FOUND));
    }
}
