package silverbars;

import silverbars.model.Order;
import silverbars.model.OrderType;
import silverbars.model.Orders;
import silverbars.service.OrderSummariser;
import silverbars.service.OrderSummariserImpl;

import java.util.List;
import java.util.stream.Collectors;

public class Marketplace {

    private final OrderSummariser orderSummariser = new OrderSummariserImpl();
    private final Orders orders = new Orders();

    public void register(final Order order) {
        orders.add(order);
    }

    public void cancel(final Order order) {
        orders.remove(order);
    }

    public Orders retrieve() {
        return orders;
    }

    public List<String> liveOrderBoard(final OrderType orderType) {
        return orderSummariser.summarise(orders, orderType).stream()
                .map(summary -> summary.getQuantity() + " kg for £" + summary.getPrice())
                .collect(Collectors.toList());
    }
}
