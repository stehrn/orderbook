package org.orderbook;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.orderbook.model.ByLevelOrderBookTest;
import org.orderbook.model.ByLevelOrderBookManagerTest;
import org.orderbook.model.OrderBookManagerTest;
import org.orderbook.model.OrderFactoryTest;

/**
 * Created by Nik on 17/06/2015.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        OrderFactoryTest.class,
        OrderBookManagerTest.class,
        ByLevelOrderBookManagerTest.class,
        ByLevelOrderBookTest.class
})
public class TestSuite {
}
