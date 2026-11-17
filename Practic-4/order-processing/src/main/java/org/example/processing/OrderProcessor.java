package org.example.processing;

import org.example.model.Order;
import org.example.model.Product;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderProcessor <T extends Product> { // Т - тип товару: Product або його підклас

    private Order<T> order; // Зберігає посилання на товар вибраного типу

    public void process() {
        if (order == null) {
            throw new IllegalArgumentException("Замовлення не передано");
        }

        if (order.getProduct() == null) {
            throw new IllegalArgumentException("Товар не передано");
        }
        System.out.println("Потік: " + Thread.currentThread().getName()
                + ", товар: " + order.getProduct().getName());
        }


    public void processSafely() {
        try {
            process();

        } catch (IllegalArgumentException i) {
            System.out.println(i.getMessage());
        }
    }
}
