package com.geniusver.persistence.example.v1.order.infrastructure.repo;

import com.geniusver.persistence.example.v1.order.domain.model.Order;
import com.geniusver.persistence.example.v1.order.domain.model.Order.OrderBuilder;
import com.geniusver.persistence.example.v1.order.domain.model.Order.OrderId;
import com.geniusver.persistence.example.v1.order.domain.model.UserId;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-16T16:31:34+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (AdoptOpenJDK)"
)
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderDO convertToDO(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderDO orderDO = new OrderDO();

        orderDO.setOrderId( orderOrderIdValue( order ) );
        orderDO.setUserId( orderUserIdValue( order ) );
        orderDO.setPrice( order.getPrice() );

        return orderDO;
    }

    @Override
    public Order createFromDO(OrderDO orderDo) {
        if ( orderDo == null ) {
            return null;
        }

        OrderBuilder order = Order.builder();

        order.orderId( orderDOToOrderId( orderDo ) );
        order.userId( orderDOToUserId( orderDo ) );
        order.price( orderDo.getPrice() );

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

    protected OrderId orderDOToOrderId(OrderDO orderDO) {
        if ( orderDO == null ) {
            return null;
        }

        long value = 0L;

        if ( orderDO.getOrderId() != null ) {
            value = orderDO.getOrderId();
        }

        OrderId orderId = new OrderId( value );

        return orderId;
    }

    protected UserId orderDOToUserId(OrderDO orderDO) {
        if ( orderDO == null ) {
            return null;
        }

        long value = 0L;

        if ( orderDO.getUserId() != null ) {
            value = orderDO.getUserId();
        }

        UserId userId = new UserId( value );

        return userId;
    }
}
