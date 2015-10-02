package org.orderbook.iface;

import org.orderbook.model.Order;

/**
 * Created by Nik on 14/06/2015.
 */
public interface OrderBook {

    void add(Order order);

    void edit(Order order);

    boolean remove(Order order);
}
