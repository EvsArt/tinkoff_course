package edu.hw8.task1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class Server {

    private int port;
    private final ServerSocket server;
    private final ExecutorService threadPoolService;
    private final QuoteService quoteService;
    private boolean closed = false;

    private Server(int port) {
        this.port = port;
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            log.error(String.format("Error with creating a server socket at port %d", port));
            throw new RuntimeException(e);
        }
        threadPoolService = Executors.newFixedThreadPool(Constants.SERVER_THREADS_COUNT);
        quoteService = new QuoteService();

        // for not-blocking connection handling
        new Thread(this::connectionsHandler).start();
    }

    public static Server start(int port) {
        Server server = new Server(port);
        log.info(String.format("Server started at port %d", port));
        return server;
    }

    public void close() {
        this.closed = true;
        // closing last waiting socket
        try (Socket ignored = new Socket(InetAddress.getByName(Constants.SERVER_NAME), port)) {
            log.info(String.format("Server closed on port %d", this.port));
        } catch (IOException e) {
            log.error("Last waited socket is unavailable");
            throw new RuntimeException(e);
        }
    }

    private void connectionsHandler() {

        while (!closed) {
            try {
                Socket socket = server.accept();
                threadPoolService.submit(() -> handleConnection(socket));
            } catch (IOException e) {
                log.error("Error with creating connection!");
                throw new RuntimeException(e);
            }
        }

    }

    private void handleConnection(Socket socket) {
        log.info(String.format("%s: Handling connection: %s", Thread.currentThread(), socket));
        ByteBuffer buffer = ByteBuffer.allocate(Constants.REQUEST_RESPONSE_BUFFER_SIZE);

        try (var reader = new DataInputStream(socket.getInputStream());
             var writer = new DataOutputStream(socket.getOutputStream())) {

            int reqSize = reader.read(buffer.array());
            byte[] reqBytes = new byte[reqSize];
            buffer.get(reqBytes);
            String request = new String(reqBytes);
            log.info(String.format("%s: Request: %s", Thread.currentThread(), request));

            String response = getQuote(request);
            log.info(String.format("%s: Response: %s", Thread.currentThread(), response));

            writer.write(response.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getQuote(String key) {
        return quoteService.getQuoteByWord(key);
    }

}
