package silverbars.service;

import silverbars.model.OrderType;

import java.util.Comparator;

public class OrderSummaryComparator {

    public Comparator<OrderSummary.Summary> order(OrderType orderType) {
        return (o1, o2) -> orderType == OrderType.BUY ? descendingPriceOrder(o1, o2) : ascendingPriceOrder(o1, o2);
    }

    private int ascendingPriceOrder(OrderSummary.Summary o1, OrderSummary.Summary o2) {
        return o1.getPrice().getPerKilo().compareTo(o2.getPrice().getPerKilo());
    }

    private int descendingPriceOrder(OrderSummary.Summary o1, OrderSummary.Summary o2) {
        return o2.getPrice().getPerKilo().compareTo(o1.getPrice().getPerKilo());
    }
}
