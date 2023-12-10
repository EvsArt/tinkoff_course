package edu.hw8.task2;

public interface ThreadPool {

    void start();

    void execute(Runnable runnable);

}
