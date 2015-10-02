package org.orderbook.consumer;

import com.sun.istack.internal.logging.Logger;
import org.orderbook.iface.Action;
import org.orderbook.iface.OrderBook;
import org.orderbook.iface.OrderConsumer;
import org.orderbook.model.Order;

import java.util.EnumMap;
import java.util.function.Consumer;

public class OrderConsumerImpl implements OrderConsumer {

    private static final Logger logger = Logger.getLogger(OrderConsumerImpl.class);

    private OrderBook orderBook;
    private Consumer<OrderBook> finishCallback;
    private final EnumMap<Action, Consumer<Order>> actions = new EnumMap<>(Action.class);

    public OrderConsumerImpl(OrderBook orderBook, Consumer<OrderBook> finishCallback) {
        this.orderBook = orderBook;
        this.finishCallback = finishCallback;
        actions.put(Action.ADD, orderBook::add);
        actions.put(Action.EDIT, orderBook::edit);
        actions.put(Action.REMOVE, orderBook::remove);
    }

    @Override
    public void startProcessing() {
        logger.info("Start processing");
    }

    @Override
    public void finishProcessing() {
        logger.info("Finish processing");
        finishCallback.accept(orderBook);
    }

    @Override
    public void notifyOrder(Action action, Order order) {
        actions.get(action).accept(order);
    }
}
