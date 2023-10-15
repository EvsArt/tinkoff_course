package edu.hw2;

import java.util.Random;

public class Task3 {

    private Random random = new Random();
    public final double chanceOfFaultyConnection = 0.5;
    public final double chanceOfExceptionInBadConnection = 0.7;

    private final String connectionStarting = "Connection starting";
    private final String connectionClosing = "Connection has closed";

    private void log(String msg) {
//        System.out.println(msg);
    }

    private String commandStarting(String command) {
        return "Command " + command + " has started";
    }

    public interface Connection extends AutoCloseable {
        void execute(String command);
    }

    public interface ConnectionManager {
        Connection getConnection();
    }

    public class ConnectionException extends RuntimeException {
    }

    public final class PopularCommandExecutor {
        private final ConnectionManager manager;
        private final int maxAttempts;

        public PopularCommandExecutor(ConnectionManager manager, int maxAttempts) {
            this.manager = manager;
            this.maxAttempts = maxAttempts;
        }

        public void updatePackages() {
            tryExecute("apt update && apt upgrade -y");
        }

        void tryExecute(String command) throws RuntimeException {
            for (int attempt = 1; attempt <= maxAttempts; attempt++) {
                try (Connection connection = manager.getConnection()) {

                    connection.execute(command);
                    return;                             // If success exit the method

                } catch (Exception e) {
                    if (attempt == maxAttempts) {       // If the last attempt failed
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public class StableConnection implements Connection {

        public StableConnection() {
            log(connectionStarting);
        }

        @Override
        public void execute(String command) {
            log(commandStarting(command));
        }

        @Override
        public void close() throws Exception {
            log(connectionClosing);
        }
    }

    public class FaultyConnection implements Connection {

        public FaultyConnection() {
            try {
                boolean willThrowException = random.nextDouble() < chanceOfExceptionInBadConnection;

                if (willThrowException) {
                    throw new ConnectionException();
                } else {
                    log(connectionStarting);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        @Override
        public void execute(String command) {

            log(commandStarting(command));

        }

        @Override
        public void close() throws Exception {
            log(connectionClosing);
        }
    }

    public class DefaultConnectionManager implements ConnectionManager {

        @Override
        public Connection getConnection() throws ConnectionException {
            boolean isConnectionFaulty = random.nextDouble() < chanceOfFaultyConnection;

            if (isConnectionFaulty) {
                return new FaultyConnection();
            } else {
                return new StableConnection();
            }
        }
    }

    public class FaultyConnectionManager implements ConnectionManager {

        @Override
        public Connection getConnection() {
            return new FaultyConnection();
        }
    }

}
