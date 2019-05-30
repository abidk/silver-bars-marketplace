package silverbars.model;

import java.math.BigDecimal;
import java.util.Objects;

public final class Price {

    private final BigDecimal perKilo;

    private Price(BigDecimal perKilo) {
        this.perKilo = perKilo;
    }

    public static Price of(final String pricePerKilo) {
        return new Price(new BigDecimal(pricePerKilo));
    }

    public BigDecimal getPerKilo() {
        return perKilo;
    }

    @Override
    public String toString() {
        return perKilo.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Price)) {
            return false;
        }

        final Price other = (Price) obj;
        return Objects.equals(this.perKilo, other.perKilo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getPerKilo());
    }
}
