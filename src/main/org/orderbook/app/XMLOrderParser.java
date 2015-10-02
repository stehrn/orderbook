package org.orderbook.app;

import org.orderbook.iface.Action;
import org.orderbook.model.OrderFactory;
import org.orderbook.model.OrderType;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.orderbook.model.Order;
import org.orderbook.iface.OrderEventHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.logging.Logger;

/**
 */
public class XMLOrderParser extends DefaultHandler {
    private static final String COMMANDS = "commands";

    private final OrderEventHandler handler;
    private final OrderFactory<Attributes> orderFactory;

    public XMLOrderParser(OrderEventHandler handler) {
        this.handler = handler;
        this.orderFactory = createOrderFactory();
    }

    private OrderFactory<Attributes> createOrderFactory() {
        return new OrderFactory<>(
                attributes -> Long.valueOf(attributes.getValue("order-id")),
                attributes -> Integer.valueOf(attributes.getValue("price")),
                attributes -> Integer.valueOf(attributes.getValue("quantity")),
                attributes -> attributes.getValue("symbol"),
                attributes -> OrderType.valueOf(attributes.getValue("type").toUpperCase())
        );
    }

    public void parseDocument(String fileName) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory.newInstance().newSAXParser().parse(fileName, this);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equals(COMMANDS)) {
            return;
        }

        Action action = Action.valueOf(qName.toUpperCase());
        Order order = orderFactory.create(action, attributes);
        handler.notifyOrder(action, order);
    }
}
