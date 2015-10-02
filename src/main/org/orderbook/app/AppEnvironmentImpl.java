package org.orderbook.app;

import com.sun.istack.internal.logging.Logger;
import org.orderbook.iface.*;
import org.orderbook.model.Order;

import java.util.LinkedHashSet;
import java.util.Set;

public class AppEnvironmentImpl implements AppEnvironment, OrderEventHandler {

    private static final Logger logger = Logger.getLogger(AppEnvironmentImpl.class);

    private final Set<OrderConsumer> consumers = new LinkedHashSet<OrderConsumer>();

    private final FeedOrders orders;

    public AppEnvironmentImpl(FeedOrders orders)
    {
        this.orders = orders;
    }

    @Override
    public void registerHandler(OrderConsumer handler) {
        consumers.add(handler);
    }

    @Override
    public final void run() {
        notifyStart();
        try {
            orders.create(this);
        } catch (Exception e) {
            logger.severe("Error creating orders", e);
        } finally {
            notifyFinish();
        }
    }

    /**
     * Invokes {@link OrderConsumer#handleEvent(org.orderbook.iface.Action, org.orderbook.model.Order)} for every registered consumer with
     * specified <code>action</code> and <code>order</code>.
     */
    @Override
    public void notifyOrder(Action action, Order order) {
        consumers.forEach(consumer -> consumer.notifyOrder(action, order));
    }

    private void notifyStart() {
        consumers.forEach(OrderConsumer::startProcessing);
    }

    private void notifyFinish() {
        consumers.forEach(OrderConsumer::finishProcessing);
    }
}
