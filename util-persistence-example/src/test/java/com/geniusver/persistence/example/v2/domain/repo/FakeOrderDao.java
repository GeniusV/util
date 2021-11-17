package com.geniusver.persistence.example.v2.domain.repo;

import cn.hutool.core.text.StrFormatter;
import com.geniusver.persistence.example.v2.infra.OrderDao;
import com.geniusver.persistence.example.v2.infra.OrderDo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * FakeOrderDao
 *
 * @author GeniusV
 */
public class FakeOrderDao implements OrderDao {
    private Map<Long, OrderDo> map = new ConcurrentHashMap<>();
    private boolean printLogs = true;

    @Override
    public OrderDo query(Long value) {
        return map.get(value);
    }

    @Override
    public OrderDo insert(OrderDo orderDo) {
        orderDo.setVersion(1L);
        if (printLogs) {
            System.out.println(StrFormatter.format("OrderDao: insert {} -> {}", orderDo.getId(), orderDo));
        }
        map.put(orderDo.getId(), orderDo);
        return orderDo;
    }

    @Override
    public OrderDo update(OrderDo orderDo) {
        OrderDo orderDo1 = map.get(orderDo.getId());
        if (orderDo1 == null) {
            throw new RuntimeException(StrFormatter.format("order not found for id '{}'", orderDo.getId()));
        }

        if (!orderDo1.getVersion().equals(orderDo.getVersion())) {
            throw new RuntimeException(StrFormatter.format("order {} version {} is outdated, current is {}",
                    orderDo.getId(), orderDo.getVersion(), orderDo1.getVersion()));
        }

        orderDo.setVersion(orderDo.getVersion() + 1);
        if (printLogs) {
            System.out.println(StrFormatter.format("OrderDao: update {} -> {}", orderDo.getId(), orderDo));
        }
        map.put(orderDo.getId(), orderDo);
        return orderDo;
    }

    public Map<Long, OrderDo> getMap() {
        return map;
    }

    public void setMap(Map<Long, OrderDo> map) {
        this.map = map;
    }

    public boolean isPrintLogs() {
        return printLogs;
    }

    public void setPrintLogs(boolean printLogs) {
        this.printLogs = printLogs;
    }
}
