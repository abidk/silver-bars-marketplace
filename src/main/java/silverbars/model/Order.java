package silverbars.model;

import java.util.Objects;

public final class Order {

    private final UserId id;
    private final Quantity quantity;
    private final Price price;
    private final OrderType orderType;

    private Order(UserId id, Quantity quantity, Price price, OrderType orderType) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.orderType = orderType;
    }

    public UserId getId() {
        return id;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public Price getPrice() {
        return price;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Order)) {
            return false;
        }

        final Order other = (Order) obj;
        return Objects.equals(this.id, other.id) &&
                Objects.equals(this.price, other.price) &&
                Objects.equals(this.quantity, other.quantity) &&
                Objects.equals(this.orderType, other.orderType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.quantity, this.price, this.orderType);
    }

    public static class Builder {

        private UserId id;
        private Quantity quantity;
        private Price price;
        private OrderType orderType;

        public Builder userId(UserId id) {
            this.id = id;
            return this;
        }

        public Builder quantity(Quantity quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder price(Price price) {
            this.price = price;
            return this;
        }

        public Builder orderType(OrderType orderType) {
            this.orderType = orderType;
            return this;
        }

        public Order build() {
            return new Order(id, quantity, price, orderType);
        }
    }
}
