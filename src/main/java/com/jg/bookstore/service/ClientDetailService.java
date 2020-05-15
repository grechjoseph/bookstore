package com.jg.bookstore.service;

import com.jg.bookstore.domain.entity.ClientDetail;
import org.springframework.security.oauth2.provider.ClientDetailsService;

import java.util.UUID;

public interface ClientDetailService extends ClientDetailsService {

    ClientDetail createClientDetail(final ClientDetail clientDetail);

    ClientDetail getClientDetailById(final UUID id);

    ClientDetail updateClientDetail(final UUID id, final ClientDetail newValues);

}
