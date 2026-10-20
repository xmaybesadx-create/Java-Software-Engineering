package org.example;
import lombok.Data;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.Locale;

@Data
public class OrderHistory
{
    private List<Order> orders;
    private final String FILE_PATH = "orders_history.txt";

    public OrderHistory()
    {
        this.orders = new ArrayList<>();
        loadFromFile();
    }

    // Не перезаписуємо пошкоджену історію новими даними.
    private boolean loadFailed;

    public boolean addOrder(Order order)
    {
        if (loadFailed) {
            System.err.println("Спочатку виправте або перейменуйте пошкоджений orders_history.txt і перезапустіть програму.");
            return false;
        }
        orders.add(order);
        if (!saveToFile()) {
            orders.remove(orders.size() - 1);
            return false;
        }
        return true;
    }

    public List<Order> getOrders() { return new ArrayList<>(orders); }
    public boolean isEmpty() { return orders.isEmpty(); }
    public int getSize() { return orders.size(); }

    private boolean saveToFile()
    {
        Path temporary = Paths.get(FILE_PATH + ".tmp");
        // Спочатку повністю записуємо тимчасовий файл, потім замінюємо історію.
        try {
            try (BufferedWriter writer = Files.newBufferedWriter(temporary, java.nio.charset.StandardCharsets.UTF_8)) {
                writer.write("HISTORY_VERSION:1\nORDERS:" + orders.size() + "\n");
                for (int i = 0; i < orders.size(); i++) {
                    Order order = orders.get(i);
                    writer.write("ORDER:" + (i + 1) + "\n");
                    writer.write("DATE:" + order.getCreatedAt() + "\n");
                    writer.write("STATUS:" + order.getStatus() + "\n");
                    writer.write("TOTAL:" + String.format(Locale.US, "%.2f", order.getTotalPrice()) + "\n");
                    writer.write("PRODUCT_COUNT:" + order.getProducts().size() + "\n");
                    for (Product p : order.getProducts()) {
                        writer.write("PRODUCT:" + p.getId() + "|" + p.getName() + "|"
                                + String.format(Locale.US, "%.2f", p.getPrice()) + "|"
                                + p.getDescription() + "|" + p.getCategory().getName() + "\n");
                    }
                    writer.write("END_ORDER\n");
                }
            }
            Files.move(temporary, Paths.get(FILE_PATH), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            System.err.println("Помилка збереження історії: " + e.getMessage());
            return false;
        }
    }

    private void loadFromFile()
    {
        Path path = Paths.get(FILE_PATH);
        if (!Files.exists(path)) return;
        List<Order> loaded = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(path, java.nio.charset.StandardCharsets.UTF_8)) {
            String line;
            Order currentOrder = null;
            int expectedProducts = -1;
            int expectedOrders = -1;
            boolean hasTotal = false;
            double savedTotal = 0;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("HISTORY_VERSION:")) {
                    if (!line.equals("HISTORY_VERSION:1")) throw new IllegalArgumentException("Невідома версія історії");
                } else if (line.startsWith("ORDERS:")) {
                    expectedOrders = Integer.parseInt(line.substring(7));
                } else if (line.startsWith("ORDER:")) {
                    if (currentOrder != null) throw new IllegalArgumentException("Незавершене замовлення");
                    currentOrder = new Order(new Cart());
                    currentOrder.setCreatedAt("Невідомо");
                    expectedProducts = -1;
                    hasTotal = false;
                } else if (currentOrder != null) {
                    if (line.startsWith("DATE:")) currentOrder.setCreatedAt(line.substring(5));
                    else if (line.startsWith("STATUS:")) currentOrder.setStatus(line.substring(7));
                    else if (line.startsWith("TOTAL:")) {
                        savedTotal = Double.parseDouble(line.substring(6));
                        hasTotal = true;
                    } else if (line.startsWith("PRODUCT_COUNT:")) {
                        expectedProducts = Integer.parseInt(line.substring(14));
                    } else if (line.startsWith("PRODUCT:")) {
                        String[] parts = line.substring(8).split("\\|", -1);
                        if (parts.length != 5) throw new IllegalArgumentException("Невірний формат товару");
                        int id = Integer.parseInt(parts[0]);
                        double price = Double.parseDouble(parts[2]);
                        if (!Double.isFinite(price) || price < 0) throw new IllegalArgumentException("Невірна ціна");
                        currentOrder.addProduct(new Product(id, parts[1], price, parts[3], new Category(0, parts[4])));
                    } else if (line.equals("END_ORDER")) {
                        if (expectedProducts != currentOrder.getProducts().size() || !hasTotal
                                || !Double.isFinite(savedTotal)
                                || Math.abs(savedTotal - currentOrder.getTotalPrice()) > 0.011) {
                            throw new IllegalArgumentException("Невірна кількість товарів або сума");
                        }
                        currentOrder.setTotalPrice(savedTotal);
                        loaded.add(currentOrder);
                        currentOrder = null;
                    } else throw new IllegalArgumentException("Невідома строка історії");
                } else if (!line.isBlank()) throw new IllegalArgumentException("Дані поза замовленням");
            }
            if (currentOrder != null || expectedOrders != loaded.size()) {
                throw new IllegalArgumentException("Файл історії неповний");
            }
            orders.addAll(loaded);
        } catch (IOException | IllegalArgumentException e) {
            loadFailed = true;
            System.err.println("Помилка завантаження історії: " + e.getMessage());
        }
    }

    public void displayHistory()
    {
        if (isEmpty())
        {
            System.out.println(RED + "Історія порожня. Ви ще не зробили жодного замовлення." + RESET);
            return;
        }

        System.out.println(BOLD + CYAN + "═══════════════════════════════════════════" + RESET);
        System.out.println(BOLD + CYAN + "📜  ІСТОРІЯ ЗАМОВЛЕНЬ (" + orders.size() + " шт.)" + RESET);
        System.out.println(BOLD + CYAN + "═══════════════════════════════════════════" + RESET);

        for (int i = orders.size() - 1; i >= 0; i--)
        {
            Order order = orders.get(i);
            System.out.println();
            System.out.println(YELLOW + "┌──────────────────────────────────────┐" + RESET);
            System.out.println(YELLOW + "│ " + BOLD + "Замовлення #" + (i + 1) + RESET + YELLOW + "                        │" + RESET);
            System.out.println(YELLOW + "│ " + RESET + "Товари: " + order.getProducts().size() + " шт." + RESET + YELLOW + "              │" + RESET);
            System.out.println(YELLOW + "│ " + RESET + "Загалом: " + String.format("%.2f", order.getTotalPrice()) + " ₴" + RESET + YELLOW + "         │" + RESET);
            System.out.println(YELLOW + "│ " + RESET + "Статус: " + order.getStatus() + RESET + YELLOW + "                 │" + RESET);
            System.out.println(YELLOW + "└──────────────────────────────────────┘" + RESET);
        }
        System.out.println();
        System.out.println(BOLD + GREEN + "Усього замовлень: " + orders.size() + RESET);
        System.out.println(BOLD + GREEN + "Загальна сума всіх замовлень: " + String.format("%.2f", getTotalSum()) + " ₴" + RESET);
    }

    public void displayOrderDetails(int index)
    {
        if (index < 1 || index > orders.size())
        {
            System.out.println(RED + "Невірний номер замовлення!" + RESET);
            return;
        }
        Order order = orders.get(index - 1);
        System.out.println(BOLD + GREEN + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
        System.out.println(BOLD + GREEN + "📄 Деталі замовлення #" + index + RESET);
        System.out.println(BOLD + GREEN + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
        System.out.println(order);
        System.out.println(BOLD + GREEN + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
    }

    private double getTotalSum()
    {
        double sum = 0;
        for (Order order : orders) sum += order.getTotalPrice();
        return sum;
    }

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN = "\u001B[36m";
    private static final String BOLD = "\u001B[1m";
}
