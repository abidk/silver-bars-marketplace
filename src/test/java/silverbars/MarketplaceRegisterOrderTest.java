package silverbars;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import silverbars.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MarketplaceRegisterOrderTest {

    private List<Order> givenOrders = new ArrayList<>();
    private Marketplace marketplace;

    @BeforeEach
    public void setUp() {
        marketplace = new Marketplace();
    }

    @Test
    public void givenSingleOrderWhenRegisterOrderThenResultContainsSingleOrder() {
        givenOrder("A", Price.of("1"), Quantity.of("1.0"), OrderType.BUY);

        whenRegisteringOrders();

        thenMarketContainsOrders(1);
        thenMarketContainsOrder("A", Price.of("1"), Quantity.of("1.0"), OrderType.BUY);
    }

    @Test
    public void givenTwoOrdersWhenRegisterOrdersThenResultContainsTwoOrders() {
        givenOrder("A", Price.of("1"), Quantity.of("1.0"), OrderType.BUY);
        givenOrder("B", Price.of("2"), Quantity.of("2.0"), OrderType.SELL);

        whenRegisteringOrders();

        thenMarketContainsOrders(2);
        thenMarketContainsOrder("A", Price.of("1"), Quantity.of("1.0"), OrderType.BUY);
        thenMarketContainsOrder("B", Price.of("2"), Quantity.of("2.0"), OrderType.SELL);
    }

    private void givenOrder(String userId, Price price, Quantity quantity, OrderType type) {
        final Order order = new Order.Builder()
                .userId(UserId.of(userId))
                .price(price)
                .quantity(quantity)
                .orderType(type)
                .build();

        givenOrders.add(order);
    }

    private void whenRegisteringOrders() {
        givenOrders.forEach(marketplace::register);
    }

    private void thenMarketContainsOrders(int expectedSize) {
        assertEquals(expectedSize, marketplace.retrieve().stream().count());
    }

    private void thenMarketContainsOrder(String expectedUserId, Price expectedPrice, Quantity expectedQuantity, OrderType expectedType) {
        assertTrue(marketplace.retrieve().stream().anyMatch(order -> {
            return order.getId().equals(UserId.of(expectedUserId)) &&
                    order.getPrice().equals(expectedPrice) &&
                    order.getQuantity().equals(expectedQuantity) &&
                    order.getOrderType().equals(expectedType);
        }));
    }
}
