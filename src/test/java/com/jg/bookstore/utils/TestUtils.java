package com.jg.bookstore.utils;

import com.jg.bookstore.api.model.ApiAuthor;
import com.jg.bookstore.api.model.ApiBook;
import com.jg.bookstore.api.model.ApiOrderEntry;
import com.jg.bookstore.api.model.ApiPurchaseOrder;
import com.jg.bookstore.domain.entity.Author;
import com.jg.bookstore.domain.entity.Book;
import com.jg.bookstore.domain.entity.OrderEntry;
import com.jg.bookstore.domain.entity.PurchaseOrder;
import com.jg.bookstore.mapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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
    public static BigDecimal BOOK_PRICE = BigDecimal.valueOf(0.01);

    public static OrderEntry ORDER_ENTRY = new OrderEntry();
    public static UUID ORDER_ENTRY_ID = ORDER_ENTRY.getId();
    public static Integer ORDER_ENTRY_QUANTITY = 1;
    public static BigDecimal ORDER_ENTRY_FINAL_UNIT_PRICE = null;

    public static PurchaseOrder ORDER = new PurchaseOrder();
    public static UUID ORDER_ID = ORDER.getId();

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
        ORDER_ENTRY.setQuantity(ORDER_ENTRY_QUANTITY);
        ORDER_ENTRY.setFinalUnitPrice(ORDER_ENTRY_FINAL_UNIT_PRICE);

        ORDER.getOrderEntries().clear();
        ORDER.addOrderEntry(ORDER_ENTRY);
        ORDER.setOrderStatus(CREATED);

        API_AUTHOR = mapper.map(AUTHOR, ApiAuthor.class);
        API_BOOK = mapper.map(BOOK, ApiBook.class);
        API_ORDER_ENTRY = mapper.map(ORDER_ENTRY, ApiOrderEntry.class);
        API_PURCHASE_ORDER = mapper.map(ORDER, ApiPurchaseOrder.class);
        API_PURCHASE_ORDER.setOrderEntries(List.of( API_ORDER_ENTRY ));
    }

}
