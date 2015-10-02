package org.orderbook.model;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static org.orderbook.model.ByLevelOrderBook.create;

/**
 * Created by Nik on 18/06/2015.
 */
public class ByLevelOrderBookManager implements BiConsumer<Order, Stream<Order>> {

    private final Map<ByLevelOrderBook.Identifier, ByLevelOrderBook> byLevelOrderBooks = new HashMap<>();

    @Override
    public void accept(Order order, Stream<Order> orders) {
        byLevelOrderBooks.put(order.getByLevelOrderBookIdentifier(), create(order.getByLevelOrderBookIdentifier(), orders));
    }

    Map<ByLevelOrderBook.Identifier, ByLevelOrderBook> getByLevelOrderBooks() {
        return byLevelOrderBooks;
    }

    @Override
    public String toString() {
        return byLevelOrderBooks.entrySet()
                .stream()
                .map(entry -> String.format("%s\n%s", entry.getKey(), entry.getValue()))
                .collect(joining("\n"));
    }
}
