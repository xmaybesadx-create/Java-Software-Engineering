package org.example.model;
import java.math.BigDecimal;
import lombok.Builder;

public class Electronics extends Product {
    private int warrantyMonths;

    @Builder
    public Electronics(int id, String name, BigDecimal price, String description, String category, int warrantyMonths) {
        super (id, name, price, description, category);
        this.warrantyMonths = warrantyMonths;
    }
}
