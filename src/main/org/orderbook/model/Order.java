package org.orderbook.model;

import java.util.Objects;

/**
 * A Data transfer object used by {@link org.orderbook.iface.AppEnvironment} to send data used.
 */
public class Order {
    private final long orderId;
    private final String symbol;
    private final OrderType type;
    private final int price;
    private final int quantity;

    private ByLevelOrderBook.Identifier byLevelOrderBookIdentifier;

    public Order(long orderId) {
        this(orderId, null, OrderType.BUY, -1, -1);
    }

    public Order(long orderId, String symbol, OrderType type, int price, int quantity) {
        this.orderId = orderId;
        this.symbol = symbol;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
    }

    public Order edit(Order editOrder) {
        return new Order(getOrderId(), getSymbol(), geType(), editOrder.getPrice(), editOrder.getQuantity());
    }

    public long getOrderId() {
        return orderId;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean isBuy() {
        return type.isBuy();
    }

    public OrderType geType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public ByLevelOrderBook.Identifier getByLevelOrderBookIdentifier() {
        if(this.byLevelOrderBookIdentifier == null)
        {
            this.byLevelOrderBookIdentifier = new ByLevelOrderBook.Identifier(this.getSymbol(), this.geType());
        }
        return this.byLevelOrderBookIdentifier;
    }

    @Override
    public String toString() {
        return String.format("Order{orderId=%d,symbol=%s,isBuy=%s,price=%d,quantity=%d", orderId, symbol, isBuy(), price, quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId &&
                price == order.price &&
                quantity == order.quantity &&
                symbol.equals(order.symbol) &&
                type == order.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, symbol, type, price, quantity);
    }
}
