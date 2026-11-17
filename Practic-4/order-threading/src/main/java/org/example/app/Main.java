package org.example.app;
import org.example.model.Clothing;
import org.example.model.Electronics;
import org.example.model.Order;
import org.example.storage.OrderStorage;
import org.example.processing.OrderProcessor;
import org.example.threading.ThreadManager;
import java.math.BigDecimal;
import com.github.javafaker.Faker;

public class Main {
    static void main() {
        Faker faker = new Faker();

        // Створюємо товари через Builder
        Clothing tshirt = Clothing.builder()
                .id(1)
                .name("Футболка")
                .price(new BigDecimal("15.99"))
                .description(faker.lorem().sentence())
                .category("Одяг")
                .size(48)
                .build();

        Electronics smartphone = Electronics.builder()
                .id(2)
                .name("Iphone 17")
                .price(new BigDecimal("35999.00"))
                .description(faker.lorem().sentence())
                .category("Електроніка")
                .warrantyMonths(24)
                .build();

        // Створюємо замовлення.
        Order<Clothing> clothingOrder = new Order<>(101, tshirt);
        Order<Electronics> electronicsOrder = new Order<>(102, smartphone);

        // Зберігаємо прийняті замовлення.
        OrderStorage<Clothing> storage = new OrderStorage<>();
        storage.save(clothingOrder);

        OrderStorage<Electronics> storage1 = new OrderStorage<>();
        storage1.save(electronicsOrder);

        System.out.println("Замовлень одягу: " + storage.count());
        System.out.println("Замовлень електроніки: " + storage1.count());

        // Створюємо обробники замовлень
        OrderProcessor<Clothing> processor =
                new OrderProcessor<>(clothingOrder);

        OrderProcessor<Electronics> processor1 =
                new OrderProcessor<>(electronicsOrder);

        // Посилання на метод: перехоплення помилки є в processSafely()
        Runnable task = processor::processSafely;

        // Лямбда: перехоплення помилки всередині завдання
        Runnable task1 = () -> {
            try {
                processor1.process();
            } catch (IllegalArgumentException i) {
                System.out.println(i.getMessage());
            }
        };

        // Менеджер запускає кожне завдання в окремому потоці
        ThreadManager threadManager = new ThreadManager();
        threadManager.startTask(task);
        threadManager.startTask(task1);

        // Виводимо дані товарів.
        System.out.println(tshirt.getName());
        System.out.println(tshirt.getPrice());
        System.out.println(tshirt.getDescription());
        System.out.println(tshirt.getCategory());

        System.out.println(smartphone.getName());
        System.out.println(smartphone.getPrice());
        System.out.println(smartphone.getDescription());
        System.out.println(smartphone.getCategory());
    }
}