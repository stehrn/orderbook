package org.orderbook.iface;

/**
 * This interface is not thread-safe.
 */
public interface OrderConsumer extends OrderEventHandler {
    /**
     * Called by environment before any events are processed.
     */
    void startProcessing();

    /**
     * Called by the environment when no more events will be handled.
     */
    void finishProcessing();
}
