package edu.hw8;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Task3 {

    private final Map<String, String> hashToUserName;
    private final Map<String, String> userNameToPassword = new HashMap<>();
    ReadWriteLock mapsLock = new ReentrantReadWriteLock();
    private final MessageDigest oneThreadMd;
    private static final String ALGORITHM_NAME = "MD5";
    private static final String POSSIBLE_PASSWORD_CHARS =
        "0123456789abcdefghijklmnopqrstuvwxyz";
    private static final int POSSIBLE_PASSWORD_CHARS_COUNT = POSSIBLE_PASSWORD_CHARS.length();

    public Task3(String infoFromDB) {
        hashToUserName = infoFromDB.lines()
            .map(it -> it.split(" "))
            .collect(Collectors.toMap(
                (String[] it) -> it[it.length - 1].toUpperCase(),
                (String[] it) -> it[0]
            ));
        try {
            oneThreadMd = MessageDigest.getInstance(ALGORITHM_NAME);
        } catch (NoSuchAlgorithmException e) {
            log.error("Error with creating an instance of MessageDigest");
            throw new RuntimeException(e);
        }
    }

    public Map<String, String> hackBDInMultiThread(int maxPassLength, int threadsCount) {

        long passwordCount = (long) Math.pow(POSSIBLE_PASSWORD_CHARS_COUNT, maxPassLength);

        List<Future<Map<String, String>>> results = new ArrayList<>(threadsCount);
        try (ExecutorService service = Executors.newFixedThreadPool(threadsCount)) {

            for (int i = 0; i < threadsCount; i++) {
                var k = i;
                MessageDigest md = MessageDigest.getInstance(ALGORITHM_NAME);    // one md on each thread
                results.add(service.submit(() -> hackBDFromTo(
                    getNthPassword((passwordCount / threadsCount) * k),
                    getNthPassword((passwordCount / threadsCount) * k + passwordCount / threadsCount),
                    md
                )));
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("Algorithm " + ALGORITHM_NAME + " was not found");
            throw new RuntimeException(e);
        }

        for (Future<Map<String, String>> future : results) {
            try {
                userNameToPassword.putAll(future.get());
            } catch (InterruptedException | ExecutionException e) {
                log.error("Error with waiting result from Future");
                throw new RuntimeException(e);
            }
        }

        return userNameToPassword;
    }

    private String getNthPassword(long n) {

        long num = n;

        int len = getPassLenByItsNumber(n);

        for (int i = 0; i < len; i++) {
            num -= (long) Math.pow(POSSIBLE_PASSWORD_CHARS_COUNT, i);
        }

        StringBuilder builder = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char next = POSSIBLE_PASSWORD_CHARS.charAt(
                (int) ((num / Math.pow(POSSIBLE_PASSWORD_CHARS_COUNT, i))) % POSSIBLE_PASSWORD_CHARS_COUNT
            );
            builder.append(next);
        }

        return builder.reverse().toString();

    }

    protected int getPassLenByItsNumber(long num) {
        long count = 0;
        int len = 0;
        while (count <= num) {
            count += (long) Math.pow(POSSIBLE_PASSWORD_CHARS_COUNT, len++);
        }
        return len - 1;
    }

    public Map<String, String> hackBD(int maxPassLength) {
        return hackBDFromTo("", "z".repeat(maxPassLength), oneThreadMd);
    }

    private Map<String, String> hackBDFromTo(String startPassword, String endPasswordInc, MessageDigest md) {

        String password = startPassword;
        String endPass = nextPassword(endPasswordInc);

        while (!(hashToUserName.isEmpty() || password.equals(endPass))) {
            checkPassword(password, md);
            password = nextPassword(password);
        }
        return userNameToPassword;
    }

    protected String nextPassword(String password) throws NoMorePasswordsException {
        int length = password.length();

        if (password.isEmpty()) {
            return "0";     // increment password length
        }
        if (password.charAt(length - 1) == 'z') {
            return nextPassword(password.substring(0, length - 1)) + "0";
        }
        return password.substring(0, length - 1) + nextPasswordChar(password.charAt(length - 1));
    }

    protected void checkPassword(String password, MessageDigest md) {

        String hash = hash(password, md);

        if (hashToUserName.containsKey(hash)) {
            mapsLock.writeLock().lock();
            userNameToPassword.put(hashToUserName.get(hash), password);
            hashToUserName.remove(hash);
            mapsLock.writeLock().unlock();
        }
    }

    private String hash(String str, MessageDigest md) {

        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        byte[] theMD5digest = md.digest(bytes);

        return String.format("%032X", new BigInteger(1, theMD5digest));
    }

    private char nextPasswordChar(char ch) {
        if (ch == '9') {
            return 'a';
        }
        if (ch == 'z') {
            return '0';
        }
        return (char) (ch + 1);
    }

}
