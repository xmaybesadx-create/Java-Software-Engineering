module order.processing {
    requires order.storage;
    requires static lombok;

    exports org.example.processing;
}