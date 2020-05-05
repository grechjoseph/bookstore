package com.jg.bookstore.mapper;

import com.jg.bookstore.api.dto.orderentry.ApiOrderEntryExtended;
import com.jg.bookstore.domain.entity.OrderEntry;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFactory;
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
        mapperFactory.registerClassMap(mapperFactory.classMap(OrderEntry.class, ApiOrderEntryExtended.class)
                .field("book.id", "bookId")
                .byDefault()
                .toClassMap());
    }
}
