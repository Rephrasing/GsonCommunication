package io.github.rephrasing.gsoncommunication.receiver;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.SneakyThrows;

import java.net.*;
import java.io.*;

public abstract class SocketDataReceiver {

    private final Socket socket;
    private final ServerSocket server;
    private final DataInputStream in;
    private final Gson gson = new Gson();

    @SneakyThrows
    public SocketDataReceiver(int port) {
        this.server = new ServerSocket(port);
        this.socket = server.accept();
        this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

        String message;
        while (socket.isClosed()) {
            message = in.readUTF();
            onReceive(gson.fromJson(message, JsonElement.class));
        }
    }

    abstract public void onReceive(JsonElement element);
}
