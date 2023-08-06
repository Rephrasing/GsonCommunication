package io.github.rephrasing.gsoncommunication.sender;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.SneakyThrows;

import java.io.*;
import java.net.*;

public class SocketDataSender {
    private final String address;
    private final int port;
    private Socket socket;
    private DataOutputStream out;
    private final Gson gson = new Gson();

    @SneakyThrows
    public SocketDataSender(String address, int port) {
        this.address = address;
        this.port = port;
    }

    @SneakyThrows
    public void send(JsonElement element) {
        String json = gson.toJson(element);
        out.writeUTF(json);
    }

    @SneakyThrows
    public void connect() {
        this.socket = new Socket(address, port);
        this.out = new DataOutputStream(socket.getOutputStream());
    }

    @SneakyThrows
    public void closeConnection() {
        out.close();
        socket.close();
    }
}
