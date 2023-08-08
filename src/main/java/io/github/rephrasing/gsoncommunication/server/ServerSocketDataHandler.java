package io.github.rephrasing.gsoncommunication.server;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.SneakyThrows;

import java.net.*;
import java.io.*;

public abstract class ServerSocketDataHandler {

    private Socket socket;
    private final ServerSocket server;
    private BufferedReader in;
    private PrintWriter out;
    private final Gson gson = new Gson();

    @SneakyThrows
    public ServerSocketDataHandler(int port) {
        this.server = new ServerSocket(port);
        System.out.println("Listening on port " + port);
    }

    abstract public void onReceive(JsonElement element);

    @SneakyThrows
    public void send(JsonElement element) {
        String json = gson.toJson(element);
        out.write(json);
    }

    @SneakyThrows
    public void connect(int timeOut) {
        this.socket = server.accept();
        socket.setSoTimeout(timeOut);
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println("Connected by " + socket.getInetAddress().getHostAddress());
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
        socket.close();
        server.close();
    }
}
