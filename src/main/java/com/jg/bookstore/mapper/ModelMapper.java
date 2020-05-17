package com.jg.bookstore.mapper;

import com.jg.bookstore.api.model.*;
import com.jg.bookstore.domain.entity.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

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
        );

        mapperFactory.registerClassMap(mapperFactory.classMap(PurchaseOrder.class, ApiPurchaseOrder.class)
            .customize(new CustomMapper<PurchaseOrder, ApiPurchaseOrder>() {
                @Override
                public void mapAtoB(final PurchaseOrder purchaseOrder, final ApiPurchaseOrder apiPurchaseOrder, final MappingContext context) {
                    apiPurchaseOrder.setId(purchaseOrder.getId());
                    apiPurchaseOrder.setOrderStatus(purchaseOrder.getOrderStatus());
                    apiPurchaseOrder.setOrderEntries(mapAsList(purchaseOrder.getOrderEntries(), ApiOrderEntry.class));
                    apiPurchaseOrder.setTotalPrice(purchaseOrder.getTotalPrice());
                    apiPurchaseOrder.setConvertedPrice(purchaseOrder.getConvertedPrice());
                }
            })
        );

        mapperFactory.registerClassMap(mapperFactory.classMap(OrderEntry.class, ApiOrderEntry.class)
                .customize(new CustomMapper<OrderEntry, ApiOrderEntry>() {

                    @Override
                    public void mapAtoB(final OrderEntry orderEntry, final ApiOrderEntry apiOrderEntry, final MappingContext context) {
                        apiOrderEntry.setId(orderEntry.getId());
                        apiOrderEntry.setBookId(orderEntry.getBook().getId());
                        apiOrderEntry.setQuantity(orderEntry.getQuantity());
                        apiOrderEntry.setFinalUnitPrice(orderEntry.getFinalUnitPrice());
                        apiOrderEntry.setConvertedFinalUnitPrice(orderEntry.getConvertedFinalUnitPrice());
                    }

                    @Override
                    public void mapBtoA(final ApiOrderEntry apiOrderEntry, final OrderEntry orderEntry, final MappingContext context) {
                        orderEntry.setQuantity(apiOrderEntry.getQuantity());
                        orderEntry.setBookId(apiOrderEntry.getBookId());
                    }
                })
        );
    }
}
