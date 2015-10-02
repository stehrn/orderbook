package org.orderbook.model;

import java.util.List;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * Expect to create this for given symbol & order type (buy/sell)
 */
public class ByLevelOrderBook {

    private final SortedSet<ByLevelOrder> byLevelOrders;

    private static final ToIntFunction<List<Integer>> SUM_QUANTITY = (quantities) -> quantities.stream().mapToInt(q -> q).sum();

    private ByLevelOrderBook(SortedSet<ByLevelOrder> byLevelOrders) {
        this.byLevelOrders = byLevelOrders;
    }

    public static ByLevelOrderBook create(Identifier identifier, Stream<Order> orders) {

        Supplier<SortedSet<ByLevelOrder>> toSortedSet = () -> new TreeSet<>(identifier.getType().getOrderComparator());
        SortedSet<ByLevelOrder> byLevelOrders = orders
                .filter(o -> o.getByLevelOrderBookIdentifier().equals(identifier)) // filter out orders wih non matching identifier
                .collect(groupingBy(Order::getPrice, mapping(Order::getQuantity, toList()))) // group by price and quantity (as list so we can get size)
                .entrySet().stream().map(entry -> toByLevelOrder(entry.getKey(), entry.getValue())) // map to ByLevelOrder
                .collect(toCollection(toSortedSet));
        return new ByLevelOrderBook(byLevelOrders);
    }

    SortedSet<ByLevelOrder> getByLevelOrders() {
        return byLevelOrders;
    }

    private static ByLevelOrder toByLevelOrder(int price, List<Integer> quantities) {
        return new ByLevelOrder(price, SUM_QUANTITY.applyAsInt(quantities), quantities.size());
    }

    @Override
    public String toString() {
        return byLevelOrders
                .stream()
                .map(ByLevelOrder::toString)
                .collect(joining("\n"));
    }

    /**
     * Created by Nik on 19/06/2015.
     */
    public static class Identifier {

        private final String symbol;
        private final OrderType orderType;

        public Identifier(String symbol, OrderType orderType) {
            this.symbol = symbol;
            this.orderType = orderType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Identifier that = (Identifier) o;
            return Objects.equals(orderType, that.orderType) && Objects.equals(symbol, that.symbol);
        }

        @Override
        public int hashCode() {
            return Objects.hash(orderType, symbol);
        }

        @Override
        public String toString() {
            return String.format("[%s:%s]", symbol, orderType);
        }

        public OrderType getType() {
            return orderType;
        }
    }
}
