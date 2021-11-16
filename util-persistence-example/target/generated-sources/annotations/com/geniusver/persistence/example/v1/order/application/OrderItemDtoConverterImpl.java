package com.geniusver.persistence.example.v1.order.application;

import com.geniusver.persistence.example.v1.order.domain.model.OrderItem;
import com.geniusver.persistence.example.v1.order.domain.model.OrderItem.OrderItemBuilder;
import com.geniusver.persistence.example.v1.order.domain.model.OrderItem.OrderItemId;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-16T16:48:52+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (AdoptOpenJDK)"
)
public class OrderItemDtoConverterImpl implements OrderItemDtoConverter {

    @Override
    public OrderItemDto toOrderItemDto(OrderItem orderItem) {
        if ( orderItem == null ) {
            return null;
        }

        OrderItemDto orderItemDto = new OrderItemDto();

        orderItemDto.setOrderItemId( orderItemOrderItemIdValue( orderItem ) );
        orderItemDto.setItemName( orderItem.getItemName() );
        orderItemDto.setPrice( orderItem.getPrice() );
        orderItemDto.setQuantity( orderItem.getQuantity() );

        return orderItemDto;
    }

    @Override
    public OrderItem toOrderItem(OrderItemDto orderItemDto) {
        if ( orderItemDto == null ) {
            return null;
        }

        OrderItemBuilder orderItem = OrderItem.builder();

        orderItem.orderItemId( orderItemDtoToOrderItemId( orderItemDto ) );
        orderItem.itemName( orderItemDto.getItemName() );
        orderItem.price( orderItemDto.getPrice() );
        orderItem.quantity( orderItemDto.getQuantity() );

        return orderItem.build();
    }

    private Long orderItemOrderItemIdValue(OrderItem orderItem) {
        if ( orderItem == null ) {
            return null;
        }
        OrderItemId orderItemId = orderItem.getOrderItemId();
        if ( orderItemId == null ) {
            return null;
        }
        long value = orderItemId.getValue();
        return value;
    }

    protected OrderItemId orderItemDtoToOrderItemId(OrderItemDto orderItemDto) {
        if ( orderItemDto == null ) {
            return null;
        }

        long value = 0L;

        if ( orderItemDto.getOrderItemId() != null ) {
            value = orderItemDto.getOrderItemId();
        }

        OrderItemId orderItemId = new OrderItemId( value );

        return orderItemId;
    }
}
