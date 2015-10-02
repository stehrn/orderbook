package org.orderbook.model;

import java.util.Objects;

/**
 * Created by Nik on 18/06/2015.
 */
public class ByLevelOrder {

    private final int price;
    private final int quantity;
    private final int count;

    public ByLevelOrder(int price, int quantity, int count) {
        this.price = price;
        this.quantity = quantity;
        this.count = count;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ByLevelOrder that = (ByLevelOrder) o;
        return count == that.count && price == that.price && quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, quantity, count);
    }

    @Override
    public String toString() {
        return String.format("{price=%d, quantity=%d, count=%d}", price, quantity, count);
    }
}
