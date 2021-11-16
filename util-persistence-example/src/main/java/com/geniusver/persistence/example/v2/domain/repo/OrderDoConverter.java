package com.geniusver.persistence.example.v2.domain.repo;

import com.geniusver.persistence.example.v2.domain.model.Order;
import com.geniusver.persistence.example.v2.infra.OrderDo;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * OrderDoConverter
 *
 * @author GeniusV
 */
@Mapper
public interface OrderDoConverter {
    OrderDoConverter INSTANCE = Mappers.getMapper(OrderDoConverter.class);

    // TODO: 11/16/2021
    @Mapping(source = "price", target = "price.amount")
    Order toOrder(OrderDo orderDo);

    @InheritInverseConfiguration
    OrderDo toOrderDo(Order order);
}
