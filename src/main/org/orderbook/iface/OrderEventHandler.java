package org.orderbook.iface;

import org.orderbook.model.Order;

/**
 * Created by Nik on 15/06/2015.
 */
@FunctionalInterface
public interface OrderEventHandler {

    /**
     * Handles specific event with order data. The meaningful properties of the order depends on the
     * action. Note that the rest properties are with unspecified, but in usual cases invalid
     * values. meaningful props are:
     * <ul>
     * <li>For REMOVE: orderId</li>
     * <li>For EDIT: orderId, quantity and price</li>
     * <li>For ADD: orderId, symbol, isBuy, quantity, and price</li>
     *
     * @param action The action.
     * @param o      The order DTO.
     */
    void notifyOrder(Action action, Order order);
}
