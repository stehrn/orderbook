package org.orderbook.model;

import org.orderbook.iface.Action;

import java.util.EnumMap;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/**
 * Created by Nik on 15/06/2015.
 */
public class OrderFactory<T> {

    private final EnumMap<Action, Function<T, Order>> commands = new EnumMap<>(Action.class);

    public OrderFactory(ToLongFunction<T> orderId, ToIntFunction<T> price, ToIntFunction<T> quantity, Function<T, String> symbol, Function<T, OrderType> type) {
        commands.put(Action.ADD, source -> create(source, orderId, symbol, type, price, quantity));
        commands.put(Action.EDIT, source -> create(source, orderId, (a) -> null, (a) -> OrderType.BUY, price, quantity));
        commands.put(Action.REMOVE, source -> new Order(orderId.applyAsLong(source)));
    }

    public Order create(Action type, T source) {
        return commands.get(type).apply(source);
    }

    private static <T> Order create(T source, ToLongFunction<T> orderId, Function<T, String> symbol, Function<T, OrderType> type,
                                    ToIntFunction<T> price, ToIntFunction<T> quantity) {
        return new Order(
                orderId.applyAsLong(source),
                symbol.apply(source),
                type.apply(source),
                price.applyAsInt(source),
                quantity.applyAsInt(source));
    }
}
