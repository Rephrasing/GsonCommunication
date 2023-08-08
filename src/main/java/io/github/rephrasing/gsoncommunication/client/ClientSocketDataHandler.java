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
    private PrintWriter out;
    private BufferedReader in;
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
        out.write(json);
    }

    @SneakyThrows
    public void connect(int timeOut) {
        this.socket = new Socket(address, port);
        socket.setSoTimeout(timeOut);
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println("Connected to server " + address + ":" + port);
        String message;
        while (!socket.isClosed()) {
            if (!in.ready()) continue;
            message = in.readLine();
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
