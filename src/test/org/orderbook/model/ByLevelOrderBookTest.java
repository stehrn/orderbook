package org.orderbook.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Nik on 19/06/2015.
 */
public class ByLevelOrderBookTest {

    @Test
    public void createBuys() {
        Stream<Order> orders = Arrays.asList(
                new Order(0l, "IBM", OrderType.BUY, 10, 100),
                new Order(1l, "IBM", OrderType.BUY, 20, 200),
                new Order(2l, "IBM", OrderType.BUY, 20, 50)).stream();

        ByLevelOrderBook book = ByLevelOrderBook.create(new ByLevelOrderBook.Identifier("IBM", OrderType.BUY), orders);

        assertThat(book.getByLevelOrders().size(), is(2));
        // these are buys so expect highest price on top
        ByLevelOrder first = book.getByLevelOrders().first();
        assertThat(first.getPrice(), is(20));
        // we expect qty to be summed up
        assertThat(first.getQuantity(), is(250));
        assertThat(first.getCount(), is(2));

        ByLevelOrder last = book.getByLevelOrders().last();
        assertThat(last.getPrice(), is(10));
        assertThat(last.getQuantity(), is(100));
        assertThat(last.getCount(), is(1));
    }

    @Test
    public void createSells() {
        Stream<Order> orders = Arrays.asList(
                new Order(0l, "IBM", OrderType.SELL, 10, 100),
                new Order(1l, "IBM", OrderType.SELL, 20, 200),
                new Order(2l, "IBM", OrderType.SELL, 20, 50)).stream();

        ByLevelOrderBook book = ByLevelOrderBook.create(new ByLevelOrderBook.Identifier("IBM", OrderType.SELL), orders);

        assertThat(book.getByLevelOrders().size(), is(2));
        // these are buys so expect lowest price on top
        ByLevelOrder first = book.getByLevelOrders().first();
        assertThat(first.getPrice(), is(10));
        assertThat(first.getQuantity(), is(100));
        assertThat(first.getCount(), is(1));

        ByLevelOrder last = book.getByLevelOrders().last();
        assertThat(last.getPrice(), is(20));
        // we expect qty to be summed up
        assertThat(last.getQuantity(), is(250));
        assertThat(last.getCount(), is(2));
    }
}
