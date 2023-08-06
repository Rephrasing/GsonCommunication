package io.github.rephrasing.gsoncommunication.receiver;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.SneakyThrows;

import java.net.*;
import java.io.*;

public abstract class SocketDataReceiver {

    private Socket socket;
    private final ServerSocket server;
    private DataInputStream in;
    private final Gson gson = new Gson();

    public SocketDataReceiver(int port) {
        try {
            this.server = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    abstract public void onReceive(JsonElement element);

    @SneakyThrows
    public void connect() {
        this.socket = server.accept();
        this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        String message;
        while (socket.isClosed()) {
            message = in.readUTF();
            onReceive(gson.fromJson(message, JsonElement.class));
        }
    }

    @SneakyThrows
    public void closeConnection() {
        in.close();
        socket.close();
        server.close();
    }
}
