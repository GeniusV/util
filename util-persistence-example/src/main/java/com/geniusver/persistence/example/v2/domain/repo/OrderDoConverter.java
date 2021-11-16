package com.geniusver.persistence.example.v2.domain.repo;

import com.geniusver.persistence.example.v2.domain.model.Order;
import com.geniusver.persistence.example.v2.infra.OrderDo;
import org.mapstruct.Mapper;
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
    Order toOrder(OrderDo orderDo);

    OrderDo toOrderDo(Order order);
}
