package org.example.storage;
import org.example.model.Product;
import org.example.model.Order;
import java.util.List;
import java.util.ArrayList;

public class OrderStorage <S extends Product>{
    private List<Order<S>> storage = new ArrayList<>();

    public synchronized void save(Order<S> order) {
        storage.add(order);
    }
    public synchronized int count() {
        int result = storage.size();
        return result;

    }
}
