package com.geniusver.persistence.example.v2.domain.repo;

import com.geniusver.persistence.example.v2.domain.model.OrderItem;
import com.geniusver.persistence.example.v2.infra.OrderItemDo;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * OrderItemDoConverter
 *
 * @author GeniusV
 */
@Mapper
public interface OrderItemDoConverter {
    OrderItemDoConverter INSTANCE = Mappers.getMapper(OrderItemDoConverter.class);

    @Mapping(source = "id", target = "id.value")
    @Mapping(source = "orderId", target = "orderId.value")
    @Mapping(source = "price", target = "price.amount")
    @Mapping(source = "currency", target = "price.currency")
    @Mapping(source = "productId", target = "productId.value")
    OrderItem toOrderItem(OrderItemDo orderItemDo);

    @InheritInverseConfiguration
    OrderItemDo toOrderItemDo(OrderItem orderItem);
}
