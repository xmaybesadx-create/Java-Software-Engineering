package org.example.model;
import lombok.Builder;
import java.math.BigDecimal;

public class Clothing extends Product {
    private int size;

    @Builder
    public Clothing(int id, String name, BigDecimal price, String description, String category, int size){
        super(id, name, price, description, category);
        this.size = size;
    }
}
