package com.jg.bookstore.api.dto.order;

import com.jg.bookstore.api.dto.orderentry.ApiOrderEntry;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApiPurchaseOrder {

    private final List<ApiOrderEntry> orderEntries = new ArrayList<>();

}
