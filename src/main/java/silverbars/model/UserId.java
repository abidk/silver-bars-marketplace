package silverbars.model;

import java.util.Objects;

public final class UserId {

    private final String value;

    private UserId(String value) {
        this.value = value;
    }

    public static UserId of(String value) {
        return new UserId(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof UserId)) {
            return false;
        }

        final UserId other = (UserId) obj;
        return Objects.equals(this.value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
