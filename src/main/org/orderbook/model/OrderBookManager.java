package org.orderbook.model;

import org.orderbook.iface.OrderBook;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

/**
 * Created by Nik on 18/06/2015.
 */
public class OrderBookManager implements OrderBook {

    private final Map<Long, Order> orderByOrderId = new HashMap<>();
    private final Map<String, List<Order>> orderByOrderBookIdentifier = new HashMap<>();

    private final BiConsumer<Order, Stream<Order>> consumer;

    public OrderBookManager(BiConsumer<Order, Stream<Order>> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void add(Order order) {
        boolean duplicate = orderByOrderId.putIfAbsent(order.getOrderId(), order) != null;
        if (duplicate) {
            throw new RuntimeException("Duplicate order id: " + order.getOrderId());
        }
        orderByOrderId.put(order.getOrderId(), order);
        orderByOrderBookIdentifier.computeIfAbsent(order.getSymbol(), s -> new ArrayList<>()).add(order);
        notifyUpdate(order);
    }

    @Override
    public void edit(Order order) {
        Order original = remove(order.getOrderId(), false);
        Objects.requireNonNull(original, () -> "Trying to edit non existent order: " + order);
        add(original.edit(order));
    }

    @Override
    public boolean remove(Order order) {
        return remove(order.getOrderId(), true) != null;
    }

    @Override
    public String toString() {
        return this.consumer.toString();
    }

    private Order remove(long orderId, boolean notify) {
        Order removed = orderByOrderId.remove(orderId);

        removeOrderFromOrderBookMap(removed);

        if (notify) {
            notifyUpdate(removed);
        }
        return removed;
    }

    private void removeOrderFromOrderBookMap(Order removed) {
        boolean removedOrderBookEntry = orderByOrderBookIdentifier.get(removed.getSymbol()).remove(removed);
        if (!removedOrderBookEntry) {
            throw new RuntimeException("Failed to remove order from orderByOrderBookIdentifier " + removed);
        }
    }

    private void notifyUpdate(Order order) {
        consumer.accept(order, orderByOrderBookIdentifier.get(order.getSymbol()).stream());
    }
}
