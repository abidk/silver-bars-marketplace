package silverbars.model;

import silverbars.Streamable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class Orders implements Streamable<Order> {

    private final Set<Order> list = new HashSet<>();

    public void add(final Order order) {
        list.add(order);
    }

    public void remove(final Order order) {
        list.remove(order);
    }

    @Override
    public Iterator<Order> iterator() {
        return list.iterator();
    }
}
