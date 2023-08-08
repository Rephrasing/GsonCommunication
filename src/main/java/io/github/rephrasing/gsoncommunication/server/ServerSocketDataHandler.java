package io.github.rephrasing.gsoncommunication.server;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.SneakyThrows;

import java.net.*;
import java.io.*;

public abstract class ServerSocketDataHandler {

    private Socket socket;
    private final ServerSocket server;
    private DataInputStream in;
    private DataOutputStream out;
    private final Gson gson = new Gson();

    @SneakyThrows
    public ServerSocketDataHandler(int port) {
        this.server = new ServerSocket(port);
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
        this.socket = server.accept();
        socket.setSoTimeout(timeOut);
        this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        this.out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        String message;
        while (!socket.isClosed()) {
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
