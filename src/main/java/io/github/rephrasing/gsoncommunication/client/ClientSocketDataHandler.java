package io.github.rephrasing.gsoncommunication.client;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.SneakyThrows;

import java.io.*;
import java.net.*;

public abstract class ClientSocketDataHandler {
    private final String address;
    private final int port;
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private final Gson gson = new Gson();

    @SneakyThrows
    public ClientSocketDataHandler(String address, int port) {
        this.address = address;
        this.port = port;
    }

    abstract public void onReceive(JsonElement element);

    @SneakyThrows
    public void send(JsonElement element) {
        String json = gson.toJson(element);
        out.writeUTF(json);
        out.flush();
    }

    @SneakyThrows
    public void connect(int timeOut) {
        this.socket = new Socket(address, port);
        socket.setSoTimeout(timeOut);
        this.out = new DataOutputStream(socket.getOutputStream());
        this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        String message;
        while (!socket.isClosed()) {
            message = in.readUTF();
            onReceive(gson.fromJson(message, JsonElement.class));
        }
    }

    @SneakyThrows
    public void closeConnection() {
        in.close();
        out.close();
        socket.close();
    }
}
