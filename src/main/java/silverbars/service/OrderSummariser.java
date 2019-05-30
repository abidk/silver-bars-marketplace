package silverbars.service;

import silverbars.model.OrderType;
import silverbars.model.Orders;

public interface OrderSummariser {

    OrderSummary summarise(Orders orders, OrderType orderType);
}
