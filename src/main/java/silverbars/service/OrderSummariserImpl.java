package silverbars.service;

import silverbars.model.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class OrderSummariserImpl implements OrderSummariser {

    private final OrderSummaryComparator comparator = new OrderSummaryComparator();

    @Override
    public OrderSummary summarise(final Orders orders, final OrderType orderType) {
        final List<Order> filteredOrders = filterOrdersByType(orders, orderType);

        final List<OrderSummary.Summary> summary = summariseOrders(filteredOrders);

        final List<OrderSummary.Summary> orderedSummary = orderSummaryByPrice(summary, orderType);

        return new OrderSummary(orderedSummary);
    }

    private List<OrderSummary.Summary> orderSummaryByPrice(final List<OrderSummary.Summary> list, final OrderType orderType) {
        return list.stream().sorted(comparator.order(orderType)).collect(Collectors.toList());
    }

    private List<OrderSummary.Summary> summariseOrders(final List<Order> orders) {
        final Map<Price, List<Order>> groupedByPrice = orders.stream()
                .collect(groupingBy(Order::getPrice));

        return groupedByPrice.entrySet().stream()
                .map(entry -> {
                    final Price price = entry.getKey();
                    final List<Quantity> quantities = entry.getValue().stream().map(Order::getQuantity).collect(Collectors.toList());
                    final Quantity total = quantities.stream().reduce(Quantity.ZERO, Quantity::add);
                    return new OrderSummary.Summary(total, price);
                }).collect(Collectors.toList());
    }

    private List<Order> filterOrdersByType(final Orders orders, final OrderType orderType) {
        return orders.stream()
                .filter(order -> order.getOrderType().equals(orderType))
                .collect(Collectors.toList());
    }
}
