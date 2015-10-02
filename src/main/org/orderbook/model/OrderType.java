package org.orderbook.model;

import java.util.Comparator;

/**
 * Created by Nik on 14/06/2015.
 */
public enum OrderType {
    BUY(Comparator.comparing(ByLevelOrder::getPrice).reversed().thenComparing(ByLevelOrder::getQuantity)),
    SELL(Comparator.comparing(ByLevelOrder::getPrice).thenComparing(ByLevelOrder::getQuantity));

    private final Comparator<ByLevelOrder> orderComparator;

    OrderType(Comparator<ByLevelOrder> orderComparator) {
        this.orderComparator = orderComparator;
    }

    public static OrderType valueOf(boolean isBuy)
    {
       return isBuy ? OrderType.BUY : OrderType.SELL;
    }


    public boolean isBuy() {
        return this == BUY;
    }

    public Comparator<ByLevelOrder> getOrderComparator() {
        return orderComparator;
    }
}
