package com.geniusver.persistence.example.v1.order.application;

import com.geniusver.persistence.example.v1.order.domain.model.Order;
import com.geniusver.persistence.example.v1.order.domain.model.Order.OrderBuilder;
import com.geniusver.persistence.example.v1.order.domain.model.Order.OrderId;
import com.geniusver.persistence.example.v1.order.domain.model.OrderItem;
import com.geniusver.persistence.example.v1.order.domain.model.UserId;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.mapstruct.factory.Mappers;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-17T10:23:54+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (AdoptOpenJDK)"
)
public class OrderDtoConverterImpl implements OrderDtoConverter {

    private final OrderItemDtoConverter orderItemDtoConverter = Mappers.getMapper( OrderItemDtoConverter.class );

    @Override
    public OrderDto toOrderDTO(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderDto orderDto = new OrderDto();

        orderDto.setOrderId( orderOrderIdValue( order ) );
        orderDto.setUserId( orderUserIdValue( order ) );
        orderDto.setOrderItems( orderItemListToOrderItemDtoList( order.getOrderItems() ) );
        orderDto.setPrice( order.getPrice() );

        return orderDto;
    }

    @Override
    public Order toOrder(OrderDto orderDTO) {
        if ( orderDTO == null ) {
            return null;
        }

        OrderBuilder order = Order.builder();

        order.orderId( orderDtoToOrderId( orderDTO ) );
        order.userId( orderDtoToUserId( orderDTO ) );
        order.orderItems( orderItemDtoListToOrderItemList( orderDTO.getOrderItems() ) );
        order.price( orderDTO.getPrice() );

        return order.build();
    }

    private Long orderOrderIdValue(Order order) {
        if ( order == null ) {
            return null;
        }
        OrderId orderId = order.getOrderId();
        if ( orderId == null ) {
            return null;
        }
        long value = orderId.getValue();
        return value;
    }

    private Long orderUserIdValue(Order order) {
        if ( order == null ) {
            return null;
        }
        UserId userId = order.getUserId();
        if ( userId == null ) {
            return null;
        }
        long value = userId.getValue();
        return value;
    }

    protected List<OrderItemDto> orderItemListToOrderItemDtoList(List<OrderItem> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderItemDto> list1 = new ArrayList<OrderItemDto>( list.size() );
        for ( OrderItem orderItem : list ) {
            list1.add( orderItemDtoConverter.toOrderItemDto( orderItem ) );
        }

        return list1;
    }

    protected OrderId orderDtoToOrderId(OrderDto orderDto) {
        if ( orderDto == null ) {
            return null;
        }

        long value = 0L;

        if ( orderDto.getOrderId() != null ) {
            value = orderDto.getOrderId();
        }

        OrderId orderId = new OrderId( value );

        return orderId;
    }

    protected UserId orderDtoToUserId(OrderDto orderDto) {
        if ( orderDto == null ) {
            return null;
        }

        long value = 0L;

        if ( orderDto.getUserId() != null ) {
            value = orderDto.getUserId();
        }

        UserId userId = new UserId( value );

        return userId;
    }

    protected List<OrderItem> orderItemDtoListToOrderItemList(List<OrderItemDto> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderItem> list1 = new ArrayList<OrderItem>( list.size() );
        for ( OrderItemDto orderItemDto : list ) {
            list1.add( orderItemDtoConverter.toOrderItem( orderItemDto ) );
        }

        return list1;
    }
}
