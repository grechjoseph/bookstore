package com.jg.bookstore.mapper;

import com.jg.bookstore.api.model.ApiOrderEntry;
import com.jg.bookstore.api.model.ApiPurchaseOrder;
import com.jg.bookstore.domain.entity.OrderEntry;
import com.jg.bookstore.domain.entity.PurchaseOrder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ModelMapper extends ConfigurableMapper {

    @Getter
    private MapperFactory mapperFactory;

    /**
     * Configure method for mapping Entity objects to Api objects.
     * @param factory Factory to use.
     */
    public void configure(MapperFactory factory) {
        super.configure(factory);
        this.mapperFactory = factory;
        registerClassMaps();
    }

    private void registerClassMaps() {
        mapperFactory.registerClassMap(mapperFactory.classMap(OrderEntry.class, ApiOrderEntry.class)
                .field("book.id", "bookId")
                .byDefault()
                .toClassMap());

        mapperFactory.registerClassMap(mapperFactory.classMap(PurchaseOrder.class, ApiPurchaseOrder.class)
                .customize(new CustomMapper<PurchaseOrder, ApiPurchaseOrder>() {
                    @Override
                    public void mapAtoB(final PurchaseOrder purchaseOrder, final ApiPurchaseOrder apiPurchaseOrder, final MappingContext context) {
                        apiPurchaseOrder.setId(purchaseOrder.getId());
                        apiPurchaseOrder.setOrderStatus(purchaseOrder.getOrderStatus());
                        apiPurchaseOrder.setOrderEntries(mapAsList(purchaseOrder.getOrderEntries(), ApiOrderEntry.class));
                        apiPurchaseOrder.setTotalPrice(BigDecimal.valueOf(purchaseOrder.getOrderEntries().stream()
                                .mapToDouble(orderEntry -> orderEntry.getQuantity() * orderEntry.getBook().getPrice().doubleValue()).sum())
                                .setScale(2, RoundingMode.HALF_UP));
                    }
                })
                .toClassMap());
    }
}
