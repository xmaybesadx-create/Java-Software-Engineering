package org.example.threading;

public class ThreadManager {

    public void startTask(Runnable task) {
        Thread thread = new Thread(task);
        thread.start();
    }
}