package silverbars;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import silverbars.model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MarketplaceCancelOrderTest {

    private Marketplace marketplace;

    @BeforeEach
    public void setUp() {
        marketplace = new Marketplace();
    }

    @Test
    public void givenOneBuyOrderWhenCancellingOrderThenResultContainsZeroOrders() {
        Order orderA = givenMarketplaceOrder("A", Price.of("1"), Quantity.of("1.0"), OrderType.BUY);

        thenMarketContainsOrders(1);

        whenCancellingOrder(orderA);

        thenMarketContainsOrders(0);
    }

    @Test
    public void givenOneSellOrderWhenCancellingOrderThenResultContainsZeroOrders() {
        Order orderA = givenMarketplaceOrder("A", Price.of("1"), Quantity.of("1.0"), OrderType.SELL);

        thenMarketContainsOrders(1);

        whenCancellingOrder(orderA);

        thenMarketContainsOrders(0);
    }

    @Test
    public void givenThreeOrdersWhenCancellingOneOrderThenResultContainsCorrectRemainingOrders() {
        Order orderA = givenMarketplaceOrder("A", Price.of("1"), Quantity.of("5.99"), OrderType.BUY);
        Order orderB = givenMarketplaceOrder("B", Price.of("2"), Quantity.of("7.99"), OrderType.SELL);
        Order orderC = givenMarketplaceOrder("C", Price.of("3"), Quantity.of("9.99"), OrderType.SELL);

        thenMarketContainsOrders(3);

        whenCancellingOrder(orderB);

        thenMarketContainsOrders(2);
        thenMarketContainsOrder(orderA);
        thenMarketContainsOrder(orderC);
    }

    private Order givenMarketplaceOrder(String userId, Price price, Quantity quantity, OrderType type) {
        final Order order = new Order.Builder()
                .userId(UserId.of(userId))
                .price(price)
                .quantity(quantity)
                .orderType(type)
                .build();

        marketplace.register(order);

        return order;
    }

    private void whenCancellingOrder(Order order) {
        marketplace.cancel(order);
    }

    private void thenMarketContainsOrders(int expectedSize) {
        assertEquals(expectedSize, marketplace.retrieve().stream().count());
    }

    private void thenMarketContainsOrder(Order expectedOrder) {
        assertTrue(marketplace.retrieve().stream().anyMatch(order -> {
            return order.equals(expectedOrder);
        }));
    }
}
