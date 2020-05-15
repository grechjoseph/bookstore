package com.jg.bookstore.domain.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Currency;
import java.util.UUID;

@Data
@Entity
public class AccountConfiguration {

    @Id
    private UUID id;

    @OneToOne
    @JoinColumn(name = "account_id")
    private AccountDetail accountDetail;

    private Currency displayCurrency;

}
