package silverbars.model;

import java.math.BigDecimal;
import java.util.Objects;

public final class Quantity {

    public static final Quantity ZERO = new Quantity(BigDecimal.ZERO);

    private final BigDecimal value;

    private Quantity(BigDecimal value) {
        this.value = value;
    }

    public static Quantity of(final String quantity) {
        return new Quantity(new BigDecimal(quantity));
    }

    public Quantity add(final Quantity quantity) {
        final BigDecimal result = value.add(quantity.getValue());
        return new Quantity(result);
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Quantity)) {
            return false;
        }

        final Quantity other = (Quantity) obj;
        return Objects.equals(this.value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getValue());
    }
}
