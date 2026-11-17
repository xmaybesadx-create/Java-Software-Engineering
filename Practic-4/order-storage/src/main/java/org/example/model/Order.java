package org.example.model;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Order <O extends Product> {
    private int number; // Номер замовлення
    private O product;

}
