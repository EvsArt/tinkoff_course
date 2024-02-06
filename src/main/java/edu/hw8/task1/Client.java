package edu.hw8.task1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Client {

    private final int port;

    public Client(int port) {
        this.port = port;
    }

    public String pushRequest(String keyWord) {

        try (Socket client = new Socket(InetAddress.getByName(Constants.SERVER_NAME), port);
             var reader = new DataInputStream(client.getInputStream());
             var writer = new DataOutputStream(client.getOutputStream())) {

            writer.write(keyWord.getBytes());

            byte[] respBuf = new byte[Constants.REQUEST_RESPONSE_BUFFER_SIZE];
            reader.read(respBuf);

            return new String(respBuf).replaceAll(String.valueOf((char) 0), "");

        } catch (IOException e) {
            log.error(String.format("Error with pushing request to the server %s:%d", Constants.SERVER_NAME, port));
            throw new RuntimeException(e);
        }

    }

}
