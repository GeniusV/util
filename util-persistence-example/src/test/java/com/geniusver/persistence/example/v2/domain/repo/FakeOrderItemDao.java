package com.geniusver.persistence.example.v2.domain.repo;

import cn.hutool.core.text.StrFormatter;
import com.geniusver.persistence.example.v2.infra.OrderItemDao;
import com.geniusver.persistence.example.v2.infra.OrderItemDo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * FakeOrderItemDao
 *
 * @author GeniusV
 */
public class FakeOrderItemDao implements OrderItemDao {
    private Map<Long, OrderItemDo> map = new ConcurrentHashMap<>();

    @Override
    public List<OrderItemDo> queryByOrderId(Long value) {
        return map.values().stream()
                .filter(orderItemDo -> value.equals(orderItemDo.getOrderId()))
                .collect(Collectors.toList());
    }

    @Override
    public OrderItemDo insert(OrderItemDo orderItemDo) {
        orderItemDo.setVersion(1L);
        System.out.println(StrFormatter.format("OrderItemDao: insert {} -> {}", orderItemDo.getId(), orderItemDo));
        map.put(orderItemDo.getId(), orderItemDo);
        return orderItemDo;
    }

    @Override
    public OrderItemDo update(OrderItemDo orderItemDo) {
        OrderItemDo orderItemDo1 = map.get(orderItemDo.getId());
        if (orderItemDo1 == null) {
            throw new RuntimeException(StrFormatter.format("orderItem not found for id '{}'", orderItemDo.getId()));
        }

        if (!orderItemDo1.getVersion().equals(orderItemDo.getVersion())) {
            throw new RuntimeException(StrFormatter.format("orderItem {} version {} is outdated, current is {}",
                    orderItemDo.getId(), orderItemDo.getVersion(), orderItemDo1.getVersion()));
        }

        orderItemDo.setVersion(orderItemDo.getVersion() + 1);
        System.out.println(StrFormatter.format("OrderItemDao: update {} -> {}", orderItemDo.getId(), orderItemDo));
        map.put(orderItemDo.getId(), orderItemDo);
        return orderItemDo;
    }
}
