module order.threading {
    requires order.storage;
    requires order.processing;
    requires javafaker;
    requires java.sql;

    exports org.example.threading;
}