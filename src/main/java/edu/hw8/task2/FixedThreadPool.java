package edu.hw8.task2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class FixedThreadPool implements ThreadPool {

    private static final int PAUSE_FOR_WAITING_FREE_THREAD_MS = 10;

    private final Thread[] threads;
    private final Queue<Runnable> tasks = new LinkedList<>();

    Map<Thread, State> threadsStates = new HashMap<>();
    Map<State, Set<Thread>> threadsByStates = new HashMap<>();
    ReadWriteLock threadStatesMapLock = new ReentrantReadWriteLock();

    private FixedThreadPool(Thread[] threads) {
        this.threads = threads;
    }

    /**
     * Start a new thread in new thread
     * and wait its ending
     *
     * @param thread thread that needs to be started
     */
    private void runThread(Thread thread) {
        new Thread(() -> {
            try {
                threadStatesMapLock.writeLock().lock();

                threadsStates.put(thread, State.RUN);
                thread.start();

                threadsByStates.get(State.HAS_TASK).remove(thread);
                threadsByStates.get(State.SLEEP).remove(thread);
                threadsByStates.get(State.RUN).add(thread);
            } finally {
                threadStatesMapLock.writeLock().unlock();
            }
            try {
                thread.join();

                threadStatesMapLock.writeLock().lock();

                threadsStates.put(thread, State.SLEEP);

                threadsByStates.get(State.RUN).remove(thread);
                threadsByStates.get(State.SLEEP).add(thread);
            } catch (InterruptedException e) {
                log.error("Error with waiting thread's end");
                throw new RuntimeException(e);
            } finally {
                threadStatesMapLock.writeLock().unlock();
            }

        }).start();
    }

    /**
     * In main thread waits until any thread stops
     * and create a new thread in its place in array
     *
     * @return new thread
     */
    private Thread addNewThreadFromQueue() {

        while (threadsByStates.get(State.SLEEP).isEmpty()) {
            try {
                TimeUnit.MILLISECONDS.sleep(PAUSE_FOR_WAITING_FREE_THREAD_MS);
            } catch (InterruptedException e) {
                log.error("Error with thread sleep at waiting free thread");
                throw new RuntimeException(e);
            }
        }

        try {
            threadStatesMapLock.writeLock().lock();

            Thread freeThread = threadsByStates.get(State.SLEEP).stream().findFirst().get();

            threadsStates.remove(freeThread);
            threadsByStates.get(State.SLEEP).remove(freeThread);

            Thread newThread = null;
            for (int i = 0; i < threads.length; i++) {
                if (threads[i].equals(freeThread)) {
                    threads[i] = new Thread(tasks.poll());
                    newThread = threads[i];
                }
            }
            threadsStates.put(newThread, State.HAS_TASK);
            threadsByStates.get(State.HAS_TASK).add(newThread);

            return newThread;

        } finally {
            threadStatesMapLock.writeLock().unlock();
        }
    }

    /**
     * Takes threads from the queue in turn
     * and run them
     */
    @Override
    public void start() {
        while (!tasks.isEmpty()) {
            Thread newThread = addNewThreadFromQueue();
            runThread(newThread);
        }
    }

    /**
     * Method adds tasks in queue
     *
     * @param runnable is task that will be added in queue
     */
    @Override
    public void execute(Runnable runnable) {
        tasks.offer(runnable);
    }

    /**
     * Creates a FixedThreadPool with n threads
     * also fills all auxiliary collections
     *
     * @param threadCount count of threads in ThreadPool
     * @return new FixedThreadPool
     */
    public static FixedThreadPool create(int threadCount) {

        FixedThreadPool pool = new FixedThreadPool(
            Stream.generate(Thread::new)
                .limit(threadCount)
                .toArray(Thread[]::new)
        );

        Arrays.stream(State.values()).forEach(it -> pool.threadsByStates.put(it, new HashSet<>()));
        Arrays.stream(pool.threads).forEach(it -> pool.threadsStates.put(it, State.SLEEP));
        Arrays.stream(pool.threads).forEach(it -> pool.threadsByStates.get(State.SLEEP).add(it));
        return pool;
    }

    public enum State {
        SLEEP,
        HAS_TASK,
        RUN
    }

}
