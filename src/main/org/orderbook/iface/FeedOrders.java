package org.orderbook.iface;

/**
 * Created by Nik on 15/06/2015.
 */
@FunctionalInterface
public interface FeedOrders {

    /**
     * Sends a stream of orders to the {@link OrderConsumer}s.
     *
     * @throws Exception if there is an error.
     */
    void create(OrderEventHandler handler) throws Exception;
}
