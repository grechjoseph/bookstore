package com.jg.bookstore.utils;

import com.jg.bookstore.api.model.ApiAuthor;
import com.jg.bookstore.api.model.ApiBook;
import com.jg.bookstore.api.model.ApiOrderEntry;
import com.jg.bookstore.api.model.ApiPurchaseOrder;
import com.jg.bookstore.config.security.context.Context;
import com.jg.bookstore.domain.entity.*;
import com.jg.bookstore.domain.enums.AccountStatus;
import com.jg.bookstore.mapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.jg.bookstore.domain.enums.AccountStatus.ACTIVE;
import static com.jg.bookstore.domain.enums.OrderStatus.CREATED;

@Component
public class TestUtils {

    public static Author AUTHOR = new Author();
    public static UUID AUTHOR_ID = AUTHOR.getId();
    public static String AUTHOR_FIRST_NAME = "First Name";
    public static String AUTHOR_LAST_NAME = "Last Name";

    public static Book BOOK = new Book();
    public static UUID BOOK_ID = BOOK.getId();
    public static String BOOK_NAME = "Book Name";
    public static Integer BOOK_STOCK = 10;
    public static BigDecimal BOOK_PRICE = BigDecimal.valueOf(10.00).setScale(2);

    public static OrderEntry ORDER_ENTRY = new OrderEntry();
    public static UUID ORDER_ENTRY_ID = ORDER_ENTRY.getId();
    public static Integer ORDER_ENTRY_QUANTITY = 1;
    public static BigDecimal ORDER_ENTRY_FINAL_UNIT_PRICE = null;

    public static PurchaseOrder ORDER = new PurchaseOrder();
    public static UUID ORDER_ID = ORDER.getId();

    public static ClientDetail CLIENT_DETAIL = new ClientDetail();
    public static UUID CLIENT_DETAIL_ID = UUID.randomUUID();
    public static String CLIENT_ID = "client";
    public static String CLIENT_SECRET = "secret";

    public static Permission PERMISSION = new Permission();
    public static UUID PERMISSION_ID = UUID.randomUUID();
    public static String PERMISSION_NAME = "ADMIN";
    public static String PERMISSION_DESC = "Admin permission.";

    public static AccountDetail ACCOUNT_DETAIL = new AccountDetail();
    public static UUID ACCOUNT_ID = UUID.randomUUID();
    public static String ACCOUNT_EMAIL = "admin@mail.com";
    public static String ACCOUNT_PASSWORD = "123456";
    public static AccountStatus ACCOUNT_STATUS = ACTIVE;

    public static AccountConfiguration ACCOUNT_CONFIGURATION = new AccountConfiguration();
    public static UUID ACCOUNT_CONFIGURATION_ID = UUID.randomUUID();

    public static ApiAuthor API_AUTHOR = new ApiAuthor();
    public static ApiBook API_BOOK = new ApiBook();
    public static ApiOrderEntry API_ORDER_ENTRY = new ApiOrderEntry();
    public static ApiPurchaseOrder API_PURCHASE_ORDER = new ApiPurchaseOrder();

    private static ModelMapper mapper;

    @Autowired
    public void setModelMapper(final ModelMapper modelMapper) {
        mapper = modelMapper;
    }

    public static void reset() {
        AUTHOR.setFirstName(AUTHOR_FIRST_NAME);
        AUTHOR.setLastName(AUTHOR_LAST_NAME);
        AUTHOR.setDeleted(false);

        BOOK.setAuthor(AUTHOR);
        BOOK.setName(BOOK_NAME);
        BOOK.setStock(BOOK_STOCK);
        BOOK.setPrice(BOOK_PRICE);
        BOOK.setDeleted(false);

        ORDER_ENTRY.setBook(BOOK);
        ORDER_ENTRY.setBookId(BOOK_ID);
        ORDER_ENTRY.setQuantity(ORDER_ENTRY_QUANTITY);
        ORDER_ENTRY.setFinalUnitPrice(ORDER_ENTRY_FINAL_UNIT_PRICE);

        ORDER.getOrderEntries().clear();
        ORDER.addOrderEntry(ORDER_ENTRY);
        ORDER.setOrderStatus(CREATED);

        CLIENT_DETAIL.setId(CLIENT_DETAIL_ID);
        CLIENT_DETAIL.setClientId(CLIENT_ID);
        CLIENT_DETAIL.setSecret(CLIENT_SECRET);

        PERMISSION.setId(PERMISSION_ID);
        PERMISSION.setName(PERMISSION_NAME);
        PERMISSION.setDescription(PERMISSION_DESC);

        ACCOUNT_DETAIL.setId(ACCOUNT_ID);
        ACCOUNT_DETAIL.setEmail(ACCOUNT_EMAIL);
        ACCOUNT_DETAIL.setPassword(ACCOUNT_PASSWORD);
        ACCOUNT_DETAIL.setStatus(ACCOUNT_STATUS);
        ACCOUNT_DETAIL.setPermissions(Set.of(PERMISSION));

        ACCOUNT_CONFIGURATION.setId(ACCOUNT_CONFIGURATION_ID);
        ACCOUNT_CONFIGURATION.setAccountDetail(ACCOUNT_DETAIL);
        ACCOUNT_CONFIGURATION.setDisplayCurrency(null);
        ACCOUNT_DETAIL.setAccountConfiguration(ACCOUNT_CONFIGURATION);

        API_AUTHOR = mapper.map(AUTHOR, ApiAuthor.class);
        API_BOOK = mapper.map(BOOK, ApiBook.class);
        API_ORDER_ENTRY = mapper.map(ORDER_ENTRY, ApiOrderEntry.class);
        API_PURCHASE_ORDER = mapper.map(ORDER, ApiPurchaseOrder.class);
        API_PURCHASE_ORDER.setOrderEntries(List.of( API_ORDER_ENTRY ));
        API_PURCHASE_ORDER.setTotalPrice(BOOK_PRICE);
    }

}
