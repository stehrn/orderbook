package org.orderbook.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Nik on 19/06/2015.
 */
public class ByLevelOrderBookManagerTest {

    private ByLevelOrderBookManager byLevelOrderBookManager;

    @Before
    public void before() {
        byLevelOrderBookManager = new ByLevelOrderBookManager();
    }

    @Test
    public void test() {

        Order order0 = new Order(0l, "IBM", OrderType.SELL, 10, 100);
        Order order1 = new Order(1l, "IBM", OrderType.BUY, 20, 200);
        Order order2 = new Order(2l, "IBM", OrderType.BUY, 20, 250);
        Order order3 = new Order(3l, "VOD", OrderType.BUY, 20, 400);
        Order order4 = new Order(4l, "VOD", OrderType.BUY, 20, 400);
        Order order5 = new Order(5l, "IBM", OrderType.SELL, 20, 50);

        byLevelOrderBookManager.accept(order1, Arrays.asList(order0, order1, order2, order3, order4, order5).stream());

        // expect 1, IBM.SELL, IBM.BUY & VOD.BUY
        assertThat(byLevelOrderBookManager.getByLevelOrderBooks().size(), is(1));

        byLevelOrderBookManager.accept(order2, Arrays.asList(order0, order1, order2, order3, order4, order5).stream());
        byLevelOrderBookManager.accept(order3, Arrays.asList(order0, order1, order2, order3, order4, order5).stream());
        byLevelOrderBookManager.accept(order4, Arrays.asList(order0, order1, order2, order3, order4, order5).stream());
        byLevelOrderBookManager.accept(order5, Arrays.asList(order0, order1, order2, order3, order4, order5).stream());

        assertThat(byLevelOrderBookManager.getByLevelOrderBooks().size(), is(3));

        ByLevelOrderBook ibmBuy = byLevelOrderBookManager.getByLevelOrderBooks().get(new ByLevelOrderBook.Identifier("IBM", OrderType.BUY));
        assertThat(ibmBuy.getByLevelOrders().size(), is(1));


        ByLevelOrderBook ibmSell = byLevelOrderBookManager.getByLevelOrderBooks().get(new ByLevelOrderBook.Identifier("IBM", OrderType.SELL));
        assertThat(ibmSell.getByLevelOrders().size(), is(2));

        ByLevelOrderBook vodBuy = byLevelOrderBookManager.getByLevelOrderBooks().get(new ByLevelOrderBook.Identifier("VOD", OrderType.BUY));
        assertThat(vodBuy.getByLevelOrders().size(), is(1));
    }
}
