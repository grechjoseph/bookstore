package com.jg.bookstore.api.dto.order;

import com.jg.bookstore.api.dto.orderentry.ApiOrderEntryExtended;
import com.jg.bookstore.domain.enums.OrderStatus;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class ApiPurchaseOrderExtended {

    private final UUID id;
    private final OrderStatus orderStatus;
    private final Set<ApiOrderEntryExtended> orderEntries;

}
