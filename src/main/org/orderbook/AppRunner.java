package org.orderbook;

import org.orderbook.app.AppEnvironmentImpl;
import org.orderbook.app.XMLOrderParser;
import org.orderbook.consumer.OrderConsumerImpl;
import org.orderbook.iface.AppEnvironment;
import org.orderbook.iface.FeedOrders;
import org.orderbook.model.ByLevelOrderBookManager;
import org.orderbook.model.OrderBookManager;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class AppRunner {

    private static final Logger log = Logger.getLogger(AppRunner.class.getName());

    /**
     * @param args
     */
    public static void main(String[] args) {

        String file = new File("resources", "orders1.xml").getPath();
        FeedOrders orders = env -> new XMLOrderParser(env).parseDocument(file);
        AppEnvironment environment = new AppEnvironmentImpl(orders);
        ByLevelOrderBookManager byLevelOrderBookManager = new ByLevelOrderBookManager();
        OrderBookManager orderBook = new OrderBookManager(byLevelOrderBookManager);
        environment.registerHandler(new OrderConsumerImpl(orderBook, (book) -> log.info(book.toString())));

      //  time(environment.run());


    }


    public static void time(Runnable runnable) throws InterruptedException {
        Consumer<Long> logger = (duration) -> System.out.printf("Took: %d ms", duration);

        Instant start = Instant.now();

        runnable.run();

        long duration  = Duration.between(start, Instant.now()).toMillis();
        logger.accept(duration);

    }
}
