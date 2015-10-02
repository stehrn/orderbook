package org.orderbook.model;

import org.junit.Before;
import org.junit.Test;
import org.orderbook.iface.Action;

import java.util.HashMap;
import java.util.Map;
import java.util.function.ToLongFunction;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Nik on 16/06/2015.
 */
public class OrderFactoryTest {

    private final int price = 100;
    private final int quantity = 600;
    private final String symbol = "IBM";

    private OrderFactory<Object> factory;

    @Before
    public void before()
    {
        factory = new OrderFactory<>(
                values -> 1L,
                values -> price,
                values -> quantity,
                values -> symbol,
                values -> OrderType.BUY);
    }

    @Test
    public void add() {

        Order order = factory.create(Action.ADD, null);
        assertThat(order.getOrderId(), is(1L));
        assertThat(order.getPrice(), is(price));
        assertThat(order.getQuantity(), is(quantity));
        assertThat(order.getSymbol(), is(symbol));
        assertThat(order.geType(), is(OrderType.BUY));
    }

    @Test
    public void edit() {
        Order order = factory.create(Action.EDIT, null);
        assertThat(order.getOrderId(), is(1L));
        assertThat(order.getPrice(), is(price));
        assertThat(order.getQuantity(), is(quantity));
        assertThat(order.getSymbol(), is(nullValue()));
        assertThat(order.geType(), is(OrderType.BUY));
    }

    @Test
    public void delete() {
        Order order = factory.create(Action.REMOVE, null);
        assertThat(order.getOrderId(), is(1L));
        assertThat(order.getPrice(), is(-1));
        assertThat(order.getQuantity(), is(-1));
        assertThat(order.getSymbol(), is(nullValue()));
        assertThat(order.geType(), is(OrderType.BUY));
    }

}
