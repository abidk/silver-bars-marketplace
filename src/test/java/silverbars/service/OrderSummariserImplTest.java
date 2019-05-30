package silverbars.service;

import org.junit.jupiter.api.Test;
import silverbars.model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderSummariserImplTest {

    private Orders givenOrders = new Orders();
    private OrderSummary result;
    private OrderType givenOrderType;

    @Test
    public void givenSellOrderThenResultReturnsCorrectSummary() {
        givenOrderType(OrderType.SELL);
        givenOrder("user1", Price.of("306"), Quantity.of("3.5"), OrderType.SELL);

        whenCallingService();

        thenResultContainsSize(1);
        thenResultContains(Price.of("306"), Quantity.of("3.5"));
    }

    @Test
    public void givenBuyOrderThenResultReturnsCorrectSummary() {
        givenOrderType(OrderType.BUY);
        givenOrder("user1", Price.of("906"), Quantity.of("9.5"), OrderType.BUY);

        whenCallingService();

        thenResultContainsSize(1);
        thenResultContains(Price.of("906"), Quantity.of("9.5"));
    }

    @Test
    public void givenBuyAndSellOrdersShouldFilterAndReturnCorrectSummary() {
        givenOrderType(OrderType.BUY);
        givenOrder("user1", Price.of("106"), Quantity.of("1.5"), OrderType.BUY);
        givenOrder("user2", Price.of("206"), Quantity.of("2.5"), OrderType.SELL);

        whenCallingService();

        thenResultContainsSize(1);
        thenResultContains(Price.of("106"), Quantity.of("1.5"));
    }

    @Test
    public void givenBuyOrderContainsSamePriceShouldReturnSamePriceWithQuantityAdded() {
        givenOrderType(OrderType.BUY);
        givenOrder("user1", Price.of("100"), Quantity.of("1.0"), OrderType.BUY);
        givenOrder("user2", Price.of("100"), Quantity.of("2.0"), OrderType.BUY);

        whenCallingService();

        thenResultContainsSize(1);
        thenResultContains(Price.of("100"), Quantity.of("3.0"));
    }

    @Test
    public void givenSellOrderContainsSamePriceShouldReturnSamePriceWithQuantityAdded() {
        givenOrderType(OrderType.SELL);
        givenOrder("user1", Price.of("100"), Quantity.of("1.0"), OrderType.SELL);
        givenOrder("user2", Price.of("100"), Quantity.of("2.0"), OrderType.SELL);

        whenCallingService();

        thenResultContainsSize(1);
        thenResultContains(Price.of("100"), Quantity.of("3.0"));
    }

    @Test
    public void givenOrdersShouldFilterSellOrdersAndReturnExpectedSummary() {
        givenOrderType(OrderType.SELL);
        givenOrder("user1", Price.of("306"), Quantity.of("3.5"), OrderType.SELL);
        givenOrder("user2", Price.of("310"), Quantity.of("1.2"), OrderType.SELL);
        givenOrder("user3", Price.of("307"), Quantity.of("1.5"), OrderType.SELL);
        givenOrder("user4", Price.of("306"), Quantity.of("2.0"), OrderType.SELL);

        whenCallingService();

        thenResultContainsSize(3);
        thenResultContains(Price.of("306"), Quantity.of("5.5"));
        thenResultContains(Price.of("307"), Quantity.of("1.5"));
        thenResultContains(Price.of("310"), Quantity.of("1.2"));
    }


    private void thenResultContainsSize(int expectedSize) {
        assertEquals(expectedSize, result.stream().count());
    }

    private void thenResultContains(Price expectedPrice, Quantity expectedQuantity) {
        assertTrue(result.stream().anyMatch(summary -> summary.getQuantity().equals(expectedQuantity)
                && summary.getPrice().equals(expectedPrice)));
    }

    private void whenCallingService() {
        final OrderSummariser summariser = new OrderSummariserImpl();
        result = summariser.summarise(givenOrders, givenOrderType);
    }

    private void givenOrderType(OrderType type) {
        givenOrderType = type;
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

}