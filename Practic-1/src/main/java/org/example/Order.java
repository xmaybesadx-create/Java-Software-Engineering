package org.example;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Order
{
    private List<Product> products;
    private double totalPrice;
    private String status;
    private String createdAt;

    public Order(Cart cart)
    {
        this.products = new ArrayList<>(cart.getProducts());
        this.totalPrice = cart.getTotalPrice();
        this.status = "Нове";
        this.createdAt = java.time.LocalDateTime.now().format(
                java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
    }

    public void addProduct(Product product)
    {
        products.add(product);
        totalPrice += product.getPrice();
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("Замовлення:\n");
        for (Product product : products) {
            sb.append(product.toString()).append("\n");
        }
        sb.append("Загальна вартість:").append(totalPrice).append("\n");
        sb.append("Статус: ").append(status).append("\n");
        sb.append("Дата: ").append(createdAt);
        return sb.toString();
    }
}
