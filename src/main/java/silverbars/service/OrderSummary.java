package silverbars.service;

import silverbars.Streamable;
import silverbars.model.Price;
import silverbars.model.Quantity;

import java.util.Iterator;
import java.util.List;

public final class OrderSummary implements Streamable<OrderSummary.Summary> {

    private final List<Summary> list;

    public OrderSummary(List<Summary> list) {
        this.list = list;
    }

    @Override
    public Iterator<Summary> iterator() {
        return list.iterator();
    }

    public static final class Summary {

        private final Quantity quantity;
        private final Price price;

        public Summary(Quantity quantity, Price price) {
            this.quantity = quantity;
            this.price = price;
        }

        public Quantity getQuantity() {
            return quantity;
        }

        public Price getPrice() {
            return price;
        }
    }
}
