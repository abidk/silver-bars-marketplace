package silverbars;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import silverbars.model.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarketplaceLiveOrderBoardTest {

    private Marketplace marketplace;
    private OrderType givenOrderType;
    private List<String> result;

    @BeforeEach
    public void setUp() {
        marketplace = new Marketplace();
    }

    @Test
    public void givenOrdersShouldReturnCorrectSummaryTextInCorrectOrder() {
        givenOrderType(OrderType.SELL);
        givenOrder("user1", Price.of("306"), Quantity.of("3.5"), OrderType.SELL);
        givenOrder("user2", Price.of("310"), Quantity.of("1.2"), OrderType.SELL);
        givenOrder("user3", Price.of("307"), Quantity.of("1.5"), OrderType.SELL);
        givenOrder("user4", Price.of("306"), Quantity.of("2.0"), OrderType.SELL);

        whenCallingLiveOrderBoard();

        thenResultContainsSize(3);
        thenResultContains(0, "5.5 kg for £306");
        thenResultContains(1, "1.5 kg for £307");
        thenResultContains(2, "1.2 kg for £310");
    }

    private void thenResultContains(int idx, String expectedText) {
        assertEquals(expectedText, result.get(idx));
    }

    private void thenResultContainsSize(int expectedSize) {
        assertEquals(expectedSize, result.size());
    }

    private void whenCallingLiveOrderBoard() {
        result = marketplace.liveOrderBoard(givenOrderType);
    }

    private void givenOrder(String userId, Price price, Quantity quantity, OrderType type) {
        final Order order = new Order.Builder()
                .userId(UserId.of(userId))
                .price(price)
                .quantity(quantity)
                .orderType(type)
                .build();

        marketplace.register(order);
    }

    private void givenOrderType(OrderType type) {
        givenOrderType = type;
    }
}
