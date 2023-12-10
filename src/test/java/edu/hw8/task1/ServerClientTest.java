package edu.hw8.task1;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServerClientTest {

    @Test
    void maxThreadsCountTest() throws InterruptedException {
        // given
        int threadsCount = Constants.SERVER_THREADS_COUNT;

        // when
        List<String> results = serverResponses(threadsCount, 8080);

        // then
        assertTrue(results.size() == threadsCount && results.stream().noneMatch(String::isEmpty));

    }

    @Test
    void overMaxThreadsCountTest() throws InterruptedException {
        // given
        int threadsCount = Constants.SERVER_THREADS_COUNT + 1;

        // when
        List<String> results = serverResponses(threadsCount, 8081);

        // then
        assertTrue(results.size() == threadsCount && results.stream().noneMatch(String::isEmpty));

    }

    private List<String> serverResponses(int threadsCount, int port) throws InterruptedException {
        Server server = Server.start(port);
        Client client = new Client(port);

        String keyWord = "личность";

        List<String> results = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        Stream.generate(() -> new Thread(() -> results.add(client.pushRequest(keyWord))))
            .limit(threadsCount)
            .forEach((thread) -> {
                threads.add(thread);
                thread.start();
            });

        for (Thread thread : threads) {
            thread.join();
        }
        server.close();

        return results;
    }

}
