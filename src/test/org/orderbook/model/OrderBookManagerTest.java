package org.orderbook.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Nik on 19/06/2015.
 */
public class OrderBookManagerTest {

    private List<Stream<Order>> results;
    private OrderBookManager manager;
    private BiConsumer<Order, Stream<Order>> consumer;

    @Before
    public void before()
    {
        results = new ArrayList<>();
        consumer = (order, orders) -> { results.clear(); results.add(orders);};
        manager = new OrderBookManager(consumer);
    }

    @Test
    public void add() {
        manager.add(new Order(0l, "IBM", OrderType.SELL, 10, 100));
        assertThat(getOrders().size(), is(1));
        manager.add(new Order(02, "VOD", OrderType.SELL, 10, 100));
        assertThat(getOrders().size(), is(2));
    }

    @Test(expected = RuntimeException.class)
    public void addDuplicateId() {
        manager.add(new Order(0l, "IBM", OrderType.SELL, 10, 100));
        manager.add(new Order(0l, "IBM", OrderType.SELL, 10, 100));
    }

    @Test
    public void edit() {
        manager.add(new Order(0l, "IBM", OrderType.SELL, 10, 100));
        manager.edit(new Order(0l, null, null, 5, 50));
        List<Order> orders = getOrders();
        assertThat(orders.size(), is(1));
        Order edited = orders.get(0);
        assertThat(edited.getPrice(), is(5));
        assertThat(edited.getQuantity(), is(50));
    }

    @Test
    public void remove()
    {
        manager.add(new Order(0l, "IBM", OrderType.SELL, 10, 100));
        assertThat(getOrders().size(), is(1));
        manager.remove(new Order(0l));
        assertThat(getOrders().isEmpty(), is(true));
    }

    private List<Order> getOrders()
    {
        return results.get(0).collect(toList());
    }


    @Test(expected = NullPointerException.class)
    public void editNonExistentOrder() {
        // todo - improve this - we dont really want a NPE
        manager.edit(new Order(0l, "IBM", OrderType.SELL, 10, 100));
    }
}
