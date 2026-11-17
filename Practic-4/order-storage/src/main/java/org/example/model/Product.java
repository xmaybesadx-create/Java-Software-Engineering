package org.example.model;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter // Додає getId()
@AllArgsConstructor // Заміняє this.id
public class Product {
    private int id;  // Створили характеристики товару
    private String name;
    private BigDecimal price;
    private String description;
    private String category;
}
