package silverbars.service;

import org.junit.jupiter.api.Test;
import silverbars.model.OrderType;
import silverbars.model.Price;
import silverbars.model.Quantity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static silverbars.service.OrderSummary.Summary;

class OrderSummaryComparatorTest {

    private List<Summary> givenSummaries = new ArrayList<>();
    private OrderType givenOrderType;
    private List<Summary> results;

    @Test
    public void givenSummaryForSellOrdersShouldReturnSummaryInAscendingOrder() {
        givenOrderType(OrderType.SELL);
        Summary summary1 = givenSummary(Quantity.of("100"), Price.of("1"));
        Summary summary2 = givenSummary(Quantity.of("200"), Price.of("3"));
        Summary summary3 = givenSummary(Quantity.of("300"), Price.of("2"));
        Summary summary4 = givenSummary(Quantity.of("400"), Price.of("4"));

        whenOrderingSummary();

        thenSummaryHasSize(4);
        thenSummaryIsOrdered(0, summary1);
        thenSummaryIsOrdered(1, summary3);
        thenSummaryIsOrdered(2, summary2);
        thenSummaryIsOrdered(3, summary4);
    }

    @Test
    public void givenSummaryForBuyOrdersShouldReturnSummaryInDescendingOrder() {
        givenOrderType(OrderType.BUY);
        Summary summary1 = givenSummary(Quantity.of("100"), Price.of("1"));
        Summary summary2 = givenSummary(Quantity.of("200"), Price.of("3"));
        Summary summary3 = givenSummary(Quantity.of("300"), Price.of("2"));
        Summary summary4 = givenSummary(Quantity.of("400"), Price.of("4"));

        whenOrderingSummary();

        thenSummaryHasSize(4);
        thenSummaryIsOrdered(0, summary4);
        thenSummaryIsOrdered(1, summary2);
        thenSummaryIsOrdered(2, summary3);
        thenSummaryIsOrdered(3, summary1);
    }

    private void thenSummaryHasSize(int expectedSize) {
        assertEquals(expectedSize, results.size());
    }

    private void thenSummaryIsOrdered(int idx, Summary expectedSummary) {
        Summary resultSummary = results.get(idx);

        assertEquals(expectedSummary.getPrice(), resultSummary.getPrice());
        assertEquals(expectedSummary.getQuantity(), resultSummary.getQuantity());
    }

    private void whenOrderingSummary() {
        final OrderSummaryComparator comparator = new OrderSummaryComparator();

        results = givenSummaries.stream()
                .sorted(comparator.order(givenOrderType))
                .collect(Collectors.toList());
    }

    private Summary givenSummary(Quantity quantity, Price price) {
        final Summary result = new Summary(quantity, price);
        givenSummaries.add(result);
        return result;
    }

    private void givenOrderType(OrderType type) {
        givenOrderType = type;
    }
}